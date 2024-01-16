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
public class AdminDeleteCommandTest {

    @Autowired
    private AdminDeleteCommand adminDeleteCommand;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deleteLastAdmin() {
        String[] args = {"admin-delete", "-u", "admin", "-p", "admin", "--target", "1"};
        adminDeleteCommand.run(args);
        Assertions.assertEquals(1, adminDeleteCommand.getExitCode());
    }

    @Test
    void deleteUser() {
        User user = new User("user", "user", Role.USER, "0123456789", "test", "user");
        userRepository.save(user);
        String[] args = {"admin-delete", "-u", "admin", "-p", "admin", "--target", user.getId().toString()};
        adminDeleteCommand.run(args);
        Assertions.assertEquals(0, adminDeleteCommand.getExitCode());
    }

}
