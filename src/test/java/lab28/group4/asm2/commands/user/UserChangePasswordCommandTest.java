package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
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
public class UserChangePasswordCommandTest {

    @Autowired
    private UserChangePasswordCommand userChangePasswordCommand;

    @Autowired
    private UserRepository userRepository;

    @Test
    void changePassword() {
        String[] args = {"change-password", "-u", "admin", "-p", "admin", "--new-password", "newpassword"};
        userChangePasswordCommand.run(args);
        Assertions.assertEquals(0, userChangePasswordCommand.getExitCode());
        User user = userRepository.findByUsername("admin");
        Assertions.assertTrue(user.validatePassword("newpassword"));
    }

}
