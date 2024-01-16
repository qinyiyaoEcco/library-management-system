package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.user.UserRegisterCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRegisterCommandTest {

    @Autowired
    private UserRegisterCommand userRegisterCommand;

    @Test
    void registerExisting() {
        String[] args = {"register", "--username", "admin", "--password", "admin", "--full-name", "admin", "--email", "test@example.com", "--phone", "0123456789"};
        userRegisterCommand.run(args);
        Assertions.assertEquals(1, userRegisterCommand.getExitCode());
    }

    @Test
    void register() {
        String[] args = {"register", "--username", "test", "--password", "test", "--full-name", "test", "--email", "test@example.com", "--phone", "0123456789"};
        userRegisterCommand.run(args);
        Assertions.assertEquals(0, userRegisterCommand.getExitCode());
    }

}
