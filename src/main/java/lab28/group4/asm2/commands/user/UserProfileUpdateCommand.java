package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "profile-update", mixinStandardHelpOptions = true, description = "Update user profile")
public class UserProfileUpdateCommand extends AuthenticatedCommand {

    public UserProfileUpdateCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @CommandLine.Option(names = "--full-name", description = "Full name")
    private String fullName;

    @CommandLine.Option(names = "--email", description = "Email")
    private String email;

    @CommandLine.Option(names = "--phone", description = "Phone number")
    private String phone;

    @CommandLine.Option(names = "--new-username", description = "New username")
    private String newUsername;

    private boolean validateNewUsername(String newUsername) {
        return getUserRepository().findByUsername(newUsername) == null;
    }

    @Override
    public Integer commandCall() {
        User user = getUser();
        if (fullName != null) {
            user.setFullName(fullName);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (newUsername != null) {
            user.setUsername(newUsername);
        }
        getUserRepository().save(user);
        Application.LOG.info("User profile updated successfully");
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Update user profile";
    }
}
