package lab28.group4.asm2.commands;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.DirtiesContext;
import picocli.CommandLine;

@SpringBootTest(classes = {Application.class, AuthenticatedCommandTest.TestCommand.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticatedCommandTest {

    @TestComponent
    @CommandLine.Command(name = "test")
    static public class TestCommand extends AuthenticatedCommand {
        public TestCommand(CommandLine.IFactory factory) {
            super(Role.ADMIN, factory);
        }

        @Override
        public Integer commandCall() {
            return 0;
        }

        @Override
        public String getAuditLog() {
            return null;
        }
    }

    @Autowired
    private TestCommand testCommand;

    @Test
    void testWithoutCredentials() {
        String[] inputs = {"test"};
        testCommand.run(inputs);
        Assertions.assertEquals(1, testCommand.getExitCode());
        inputs = new String[]{"test", "--username", "admin"};
        testCommand.run(inputs);
        Assertions.assertEquals(1, testCommand.getExitCode());
        inputs = new String[]{"test", "--password", "admin"};
        testCommand.run(inputs);
        Assertions.assertEquals(1, testCommand.getExitCode());
    }

    @Test
    void testWithWrongCredentials() {
        String[] inputs = {"test", "--username", "admin", "--password", "invalid"};
        testCommand.run(inputs);
        Assertions.assertEquals(1, testCommand.getExitCode());
    }

    @Test
    void testWithCorrectCredentials() {
        String[] inputs = {"test", "--username", "admin", "--password", "admin"};
        testCommand.run(inputs);
        Assertions.assertEquals(0, testCommand.getExitCode());
    }

}
