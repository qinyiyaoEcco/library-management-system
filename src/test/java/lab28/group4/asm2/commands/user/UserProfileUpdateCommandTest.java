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
public class UserProfileUpdateCommandTest {

    @Autowired
    private UserProfileUpdateCommand userProfileUpdateCommand;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateFullName() {
        String[] args = {"profile-update", "-u", "admin", "-p", "admin", "--full-name", "test"};
        userProfileUpdateCommand.run(args);
        Assertions.assertEquals(0, userProfileUpdateCommand.getExitCode());
        Assertions.assertEquals("test", userRepository.findByUsername("admin").getFullName());
    }

    @Test
    void updateEmail() {
        String[] args = {"profile-update", "-u", "admin", "-p", "admin", "--email", "test"};
        userProfileUpdateCommand.run(args);
        Assertions.assertEquals(0, userProfileUpdateCommand.getExitCode());
        Assertions.assertEquals("test", userRepository.findByUsername("admin").getEmail());
    }

    @Test
    void updatePhone() {
        String[] args = {"profile-update", "-u", "admin", "-p", "admin", "--phone", "test"};
        userProfileUpdateCommand.run(args);
        Assertions.assertEquals(0, userProfileUpdateCommand.getExitCode());
        Assertions.assertEquals("test", userRepository.findByUsername("admin").getPhone());
    }

    @Test
    void updateUsername() {
        String[] args = {"profile-update", "-u", "admin", "-p", "admin", "--new-username", "test"};
        userProfileUpdateCommand.run(args);
        Assertions.assertEquals(0, userProfileUpdateCommand.getExitCode());
        Assertions.assertEquals("test", userRepository.findByUsername("test").getUsername());
    }

}
