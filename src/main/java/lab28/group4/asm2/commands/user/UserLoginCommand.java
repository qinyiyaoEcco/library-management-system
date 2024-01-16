package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Session;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.ScrollRepository;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "login", mixinStandardHelpOptions = true, description = "Login")
public class UserLoginCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public UserLoginCommand(ScrollRepository scrollRepository, CommandLine.IFactory factory) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @Override
    public Integer commandCall() {
        User user = getUser();
        Session.setUser(user);
        Application.LOG.info("Hello {}! You are logged in!", user.getUsername());
        if (scrollRepository.findByUser(user).isEmpty()) {
            Application.LOG.warn("You have no scrolls. Use 'scroll-add' to create one.");
        }
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Login";
    }
}
