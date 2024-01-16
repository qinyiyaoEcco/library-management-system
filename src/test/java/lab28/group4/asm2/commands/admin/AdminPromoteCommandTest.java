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
public class AdminPromoteCommandTest {

    @Autowired
    private AdminPromoteCommand adminPromoteCommand;

    @Autowired
    private UserRepository userRepository;

    @Test
    void promoteAdmin() {
        String[] args = {"admin-promote", "-u", "admin", "-p", "admin", "--target", "1"};
        adminPromoteCommand.run(args);
        Assertions.assertEquals(1, adminPromoteCommand.getExitCode());
    }

    @Test
    void promoteUser() {
        User user = new User("user", "user", Role.USER, "0123456789", "test", "user");
        userRepository.save(user);
        String[] args = {"admin-promote", "-u", "admin", "-p", "admin", "--target", user.getId().toString()};
        adminPromoteCommand.run(args);
        Assertions.assertEquals(0, adminPromoteCommand.getExitCode());
    }

}
