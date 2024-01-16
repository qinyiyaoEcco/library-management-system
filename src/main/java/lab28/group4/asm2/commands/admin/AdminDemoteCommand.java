package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "admin-demote", mixinStandardHelpOptions = true, description = "Demote an admin")
public class AdminDemoteCommand extends AdminCommand {

    public AdminDemoteCommand(CommandLine.IFactory factory) {
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
        User targetUser = userRepository.findById(targetUserId).get();
        if (targetUser.getRole() == Role.USER) {
            Application.LOG.error("User {} is already a regular user", targetUser.getUsername());
            return 1;
        }

        if (userRepository.countUserByRole(Role.ADMIN) == 1) {
            Application.LOG.error("Cannot demote the last admin");
            return 1;
        }

        targetUser.setRole(Role.USER);
        userRepository.save(targetUser);
        Application.LOG.info("User {} has been demoted to a regular user", targetUser.getUsername());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Demote user id %d", targetUserId);
    }

}