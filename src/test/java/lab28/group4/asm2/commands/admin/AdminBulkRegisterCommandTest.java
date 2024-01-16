
package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminBulkRegisterCommandTest {

    @Autowired
    private AdminBulkRegisterCommand adminBulkRegisterCommand;

    private String getResourcePath(String filename) {
        return this.getClass().getClassLoader().getResource("admin/bulk_register/" + filename).getPath();
    }

    @Test
    void invalidBlank() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("invalid_blank.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(1, adminBulkRegisterCommand.getExitCode());
    }

    @Test
    void invalidDuplicate() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("invalid_duplicate.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(1, adminBulkRegisterCommand.getExitCode());
    }

    @Test
    void invalidNotEnough() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("invalid_not_enough.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(1, adminBulkRegisterCommand.getExitCode());
    }

    @Test
    void invalidRole() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("invalid_role.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(1, adminBulkRegisterCommand.getExitCode());
    }

    @Test
    void invalidExists() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("invalid_exists.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(1, adminBulkRegisterCommand.getExitCode());
    }

    @Test
    void valid() {
        String[] args = {"register-bulk", "-u", "admin", "-p", "admin", "--file", getResourcePath("valid.txt")};
        adminBulkRegisterCommand.run(args);
        Assertions.assertEquals(0, adminBulkRegisterCommand.getExitCode());
    }
}

