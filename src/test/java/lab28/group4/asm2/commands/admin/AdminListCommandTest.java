package lab28.group4.asm2.commands.admin;


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
public class AdminListCommandTest {

    @Autowired
    private AdminListCommand AdminListCommand;

    @Test
    void userList() {
        String[] args = {"admin-list", "-u", "admin", "-p", "admin"};
        AdminListCommand.run(args);
        Assertions.assertEquals(0, AdminListCommand.getExitCode());
    }
}
