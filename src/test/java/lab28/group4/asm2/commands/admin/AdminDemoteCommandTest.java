package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminDemoteCommandTest {

    @Autowired
    private AdminDemoteCommand adminDemoteCommand;

    @Autowired
    private UserRepository userRepository;

    @Test
    void demoteLastAdmin() {
        String[] args = {"admin-demote", "-u", "admin", "-p", "admin", "--target", "1"};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(1, adminDemoteCommand.getExitCode());
    }

    @Test
    void demoteAdmin() {
        User user = new User("user", "user", Role.ADMIN, "0123456789", "test", "user");
        userRepository.save(user);
        String[] args = {"admin-demote", "-u", "admin", "-p", "admin", "--target", user.getId().toString()};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(0, adminDemoteCommand.getExitCode());
    }

    @Test
    void demoteUser() {
        User user = new User("user", "user", Role.USER, "0123456789", "test", "user");
        userRepository.save(user);
        String[] args = {"admin-demote", "-u", "admin", "-p", "admin", "--target", user.getId().toString()};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(1, adminDemoteCommand.getExitCode());
    }

}