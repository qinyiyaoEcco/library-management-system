package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
@CommandLine.Command(name = "register-bulk", mixinStandardHelpOptions = true, description = {"Register multiple users", "Format: username,password,role,phone,email,full_name"})
public class AdminBulkRegisterCommand extends AdminCommand {

    public AdminBulkRegisterCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @CommandLine.Option(names = "--file", description = "Path to file")
    @RequiredInteractive(invalidMessage = "File not found")
    private File file;

    private boolean validateFile(File file) {
        return file.exists() && file.isFile();
    }

    private int number;

    @Override
    public Integer commandCall() {
        UserRepository userRepository = getUserRepository();
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (Exception e) {
            Application.LOG.error("Unable to read file", e);
            return 1;
        }
        int index = 0;
        List<User> newUsers = new ArrayList<>();
        for (String line : lines) {
            index++;
            String[] fields = line.split(",");
            if (fields.length != 6) {
                Application.LOG.error("Invalid format on line {}", index);
                return 1;
            }
            for (int i = 0; i < fields.length; i++) {
                fields[i] = fields[i].trim();
                if (fields[i].isEmpty()) {
                    Application.LOG.error("Invalid format on line {}", index);
                    return 1;
                }
            }
            String username = fields[0];
            if (userRepository.findByUsername(username) != null) {
                Application.LOG.error("Username {} already exists on line {}", username, index);
                return 1;
            }
            String password = fields[1];
            Role role;
            try {
                role = Role.valueOf(fields[2].toUpperCase());
            } catch (Exception e) {
                Application.LOG.error("Invalid role on line {}", index);
                return 1;
            }
            if (newUsers.stream().anyMatch(user -> user.getUsername().equals(username))) {
                Application.LOG.error("Duplicate username on line {}", index);
                return 1;
            }
            User user = new User(username, password, role, fields[3], fields[4], fields[5]);
            newUsers.add(user);
        }
        userRepository.saveAll(newUsers);
        Application.LOG.info("Successfully registered {} users", newUsers.size());
        number = newUsers.size();
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Registered %d users from file %s", number, file.getAbsolutePath());
    }

}
