package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Session;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class UserLogoutCommandTest {

    @Autowired
    private UserLogoutCommand userLogoutCommand;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearSession() {
        Session.clear();
    }

    @Test
    void userLogout() {
        User user = userRepository.findByUsername("admin");
        Session.setUser(user);
        String[] logoutArgs = {"logout"};
        userLogoutCommand.run(logoutArgs);
        Assertions.assertEquals(0, userLogoutCommand.getExitCode());
    }

    @Test
    void userInvalidLogout() {
        String[] logoutArgs = {"logout"};
        userLogoutCommand.run(logoutArgs);
        Assertions.assertEquals(1, userLogoutCommand.getExitCode());
    }
}