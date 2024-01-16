package lab28.group4.asm2.commands.shell;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.shell.ShellCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class ShellCommandTest {

    private final InputStream originalIn = System.in;

    @Autowired
    private ShellCommand shellCommand;

    @Test
    void command() {
        String[] inputs = {"help", "exit"};
        InputStream in = new ByteArrayInputStream(String.join(System.lineSeparator(), inputs).getBytes());
        System.setIn(in);
        String[] args = {"shell", "--non-interactive"};
        shellCommand.run(args);
        Assertions.assertEquals(0, shellCommand.getExitCode());
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
    }

}
