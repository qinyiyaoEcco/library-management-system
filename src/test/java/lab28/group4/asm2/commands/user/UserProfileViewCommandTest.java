package lab28.group4.asm2.commands.user;


import lab28.group4.asm2.Application;
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
public class UserProfileViewCommandTest {

    @Autowired
    private UserProfileViewCommand userProfileViewCommand;

    @Test
    void viewProfile() {
        String[] args = {"profile-view", "-u", "admin", "-p", "admin"};
        userProfileViewCommand.run(args);
        Assertions.assertEquals(0, userProfileViewCommand.getExitCode());
    }
}
