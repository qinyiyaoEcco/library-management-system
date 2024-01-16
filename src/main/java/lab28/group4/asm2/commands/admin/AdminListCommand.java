package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.User;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "admin-list", description = "List all users")
public class AdminListCommand extends AdminCommand {

    public AdminListCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @Override
    public Integer commandCall() {
        List<User> allUsers = getUserRepository().findAll();

        Application.LOG.info("All Users:");
        for (User user : allUsers) {
            Application.LOG.info("User ID: {}", user.getId());
            Application.LOG.info("Username: {}", user.getUsername());
            Application.LOG.info("Role: {}", user.getRole());
            Application.LOG.info("Phone: {}", user.getPhone());
            Application.LOG.info("Email: {}", user.getEmail());
            Application.LOG.info("Full Name: {}", user.getFullName());
            Application.LOG.info("---------------");
        }

        return 0;
    }

    @Override
    public String getAuditLog() {
        return "List all users";
    }
}
