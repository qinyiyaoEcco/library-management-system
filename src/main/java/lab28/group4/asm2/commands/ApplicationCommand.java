package lab28.group4.asm2.commands;

import jakarta.annotation.Nullable;
import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.AuditLog;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.AuditLogRepository;
import org.fusesource.jansi.Ansi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import picocli.CommandLine;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;

public abstract class ApplicationCommand implements CommandLineRunner, ExitCodeGenerator, Callable<Integer> {
    private final CommandLine commandLine;
    private final String name;
    private int exitCode;

    private AuditLogRepository auditLogRepository;

    @Autowired
    private void setAuditLogRepository(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public ApplicationCommand(CommandLine.IFactory factory) {
        this.commandLine = new CommandLine(this, factory);
        CommandLine.Command annotation = this.getClass().getAnnotation(CommandLine.Command.class);
        this.name = annotation != null ? annotation.name() : null;
    }

    @Override
    public void run(String... args) {
        String command = args.length > 0 ? args[0] : null;
        if (this.name != null && !this.name.isBlank() && this.name.equals(command)) {
            exitCode = commandLine.execute(Arrays.copyOfRange(args, 1, args.length));
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    @Override
    public Integer call() {
        if (isInjectOptionsFailed()) {
            return 1;
        }
        int status = commandCall();
        if (status == 0) {
            saveAudit();
        }
        return status;
    }

    protected void saveAudit() {
        String log = getAuditLog();
        if (log != null) {
            User user = null;
            if (AuthenticatedCommand.class.isAssignableFrom(this.getClass())) {
                user = ((AuthenticatedCommand) this).getUser();
            }
            AuditLog auditLog = new AuditLog(user, getAuditLog());
            try {
                auditLogRepository.save(auditLog);
            } catch (Exception e) {
                Application.LOG.warn("Cannot save audit log");
            }
        }
    }

    abstract public Integer commandCall();

    protected boolean isInjectOptionsFailed() {
        Class<?> currentClass = this.getClass();
        Scanner scanner = new Scanner(System.in);
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(CommandLine.Option.class)) {
                    continue;
                }
                RequiredInteractive requiredInteractive = field.getAnnotation(RequiredInteractive.class);
                field.setAccessible(true);
                Object fieldValue;
                try {
                    fieldValue = field.get(this);
                } catch (IllegalAccessException e) {
                    Application.LOG.error("Cannot inject option", e);
                    return true;
                }
                if (fieldValue != null) {
                    if (isOptionValueInvalid(currentClass, field, fieldValue)) {
                        return true;
                    }
                    continue;
                }
                if (requiredInteractive == null) {
                    continue;
                }
                while (true) {
                    CommandLine.Option option = field.getAnnotation(CommandLine.Option.class);
                    String fieldName = option != null && option.description().length > 0 ? option.description()[0] : field.getName();
                    System.out.printf("%sEnter %s:%s ", Ansi.ansi().fgBrightCyan(), fieldName, Ansi.ansi().reset());
                    String value = scanner.nextLine();
                    if (value.isBlank()) {
                        printError("Value cannot be blank");
                        continue;
                    }
                    try {
                        Object convertedValue = convertValue(field.getType(), value);
                        if (isOptionValueInvalid(currentClass, field, convertedValue)) {
                            continue;
                        }
                        field.set(this, convertedValue);
                        break;
                    } catch (IllegalArgumentException e) {
                        Application.LOG.error("Invalid value", e);
                    } catch (IllegalAccessException e) {
                        Application.LOG.error("Cannot inject option", e);
                        return true;
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return false;
    }

    private boolean isOptionValueInvalid(Class<?> currentClass, Field field, Object value) {
        RequiredInteractive requiredInteractive = field.getAnnotation(RequiredInteractive.class);
        String invalidMessage = requiredInteractive != null ? requiredInteractive.invalidMessage() : "Invalid value";
        String validationMethodName = "validate" + capitalize(field.getName());
        Method validationMethod;
        try {
            validationMethod = currentClass.getDeclaredMethod(validationMethodName, field.getType());
        } catch (NoSuchMethodException e) {
            validationMethod = null;
        }
        if (validationMethod != null) {
            validationMethod.setAccessible(true);
            try {
                boolean isValid = (boolean) validationMethod.invoke(this, value);
                if (!isValid) {
                    printError(invalidMessage);
                    return true;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                Application.LOG.error("Cannot validate value", e);
                return true;
            }
        }
        return false;
    }

    private Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == Float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == Long.class) {
            return Long.parseLong(value);
        } else if (fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == String.class) {
            return value;
        } else if (fieldType == File.class) {
            return new File(value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void printError(String message) {
        System.out.printf("%s%s%s\n", Ansi.ansi().fgRed(), message, Ansi.ansi().reset().toString());
    }

    @Nullable
    abstract public String getAuditLog();

}
