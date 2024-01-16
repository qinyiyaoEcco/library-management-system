package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Session;
import lab28.group4.asm2.commands.ApplicationCommand;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "logout", mixinStandardHelpOptions = true, description = "Logout")
public class UserLogoutCommand extends ApplicationCommand {

    public UserLogoutCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @Override
    public Integer commandCall() {
        if (Session.getUser() != null) {
            Session.clear();
            Application.LOG.info("You are logged out!");
            return 0;
        } else {
            Application.LOG.info("You are currently not logged in.");
            return 1;
        }
    }

    @Override
    public String getAuditLog() {
        return "Logout";
    }
}