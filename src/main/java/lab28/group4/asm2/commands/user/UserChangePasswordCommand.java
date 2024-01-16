package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "change-password", mixinStandardHelpOptions = true, description = "Change password")
public class UserChangePasswordCommand extends AuthenticatedCommand {

    public UserChangePasswordCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @CommandLine.Option(names = "--new-password", description = "New password")
    @RequiredInteractive
    private String newPassword;

    @Override
    public Integer commandCall() {
        User user = getUser();
        user.setPassword(newPassword);
        getUserRepository().save(user);
        Application.LOG.info("Password changed successfully");
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Change password";
    }

}
