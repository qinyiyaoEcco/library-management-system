package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "admin-promote", description = "Promote an user to admin", mixinStandardHelpOptions = true)
public class AdminPromoteCommand extends AdminCommand {

    public AdminPromoteCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @CommandLine.Option(names = "--target", description = "Target user id")
    @RequiredInteractive(invalidMessage = "User not found")
    private Long targetUserId;

    private boolean validateTargetUserId(Long targetUserId) {
        return getUserRepository().findById(targetUserId).isPresent();
    }

    @Override
    public Integer commandCall() {
        UserRepository userRepository = getUserRepository();
        User user = userRepository.findById(targetUserId).get();
        if (user.getRole() == Role.ADMIN) {
            Application.LOG.error("User is already an admin");
            return 1;
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        Application.LOG.info("{} promoted successfully", user.getUsername());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Promote user id %d", targetUserId);
    }

}
