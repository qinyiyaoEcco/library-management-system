package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "admin-delete", mixinStandardHelpOptions = true, description = "Delete an user")
public class AdminDeleteCommand extends AdminCommand {

    public AdminDeleteCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @CommandLine.Option(names = "--target", description = "Target user id")
    @RequiredInteractive(invalidMessage = "User not found")
    private Long targetUserId;

    private boolean validateTargetUserId(Long targetUsername) {
        return getUserRepository().findById(targetUsername).isPresent();
    }

    @Override
    public Integer commandCall() {
        UserRepository userRepository = getUserRepository();
        User targetUser = userRepository.findById(targetUserId).get();
        if (userRepository.countUserByRole(Role.ADMIN) == 1 && targetUser.getRole() == Role.ADMIN) {
            Application.LOG.error("Cannot delete the last admin");
            return 1;
        }

        userRepository.delete(targetUser);
        Application.LOG.info("User {} has been deleted", targetUser.getUsername());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Delete user id %d", targetUserId);
    }

}
