package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class UserLoginCommandTest {

    @Autowired
    private UserLoginCommand userLoginCommand;

    @Test
    void userLogin() {
        String[] args = {"login", "--username", "admin", "--password", "admin"};
        userLoginCommand.run(args);
        Assertions.assertEquals(0, userLoginCommand.getExitCode());
    }

    @AfterEach
    void clearSession() {
        Session.clear();
    }
}
