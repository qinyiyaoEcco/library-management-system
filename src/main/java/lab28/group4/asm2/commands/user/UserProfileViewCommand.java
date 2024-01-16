package lab28.group4.asm2.commands.user;


import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "profile-view", description = "View your profile details", mixinStandardHelpOptions = true)
public class UserProfileViewCommand extends AuthenticatedCommand {

    public UserProfileViewCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @Override
    public Integer commandCall() {
        User user = getUser();

        Application.LOG.info("Full Name: " + user.getFullName());
        Application.LOG.info("Email: " + user.getEmail());
        Application.LOG.info("Phone: " + user.getPhone());
        Application.LOG.info("Role: " + user.getRole());

        return 0;
    }

    @Override
    public String getAuditLog() {
        return "View user profile";
    }
}
