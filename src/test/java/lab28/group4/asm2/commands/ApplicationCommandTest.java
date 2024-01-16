package lab28.group4.asm2.commands;

import lab28.group4.asm2.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.DirtiesContext;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest(classes = {Application.class, ApplicationCommandTest.TestCommand.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationCommandTest {

    @TestComponent
    @CommandLine.Command(name = "test")
    static public class TestCommand extends ApplicationCommand {

        @CommandLine.Option(names = "--int")
        @RequiredInteractive
        private Integer anInt;

        @CommandLine.Option(names = "--string")
        @RequiredInteractive
        private String aString;

        @CommandLine.Option(names = "--boolean")
        @RequiredInteractive
        private Boolean aBoolean;

        @CommandLine.Option(names = "--double")
        @RequiredInteractive
        private Double aDouble;

        @CommandLine.Option(names = "--float")
        @RequiredInteractive
        private Float aFloat;

        @CommandLine.Option(names = "--long")
        @RequiredInteractive
        private Long aLong;

        public TestCommand(CommandLine.IFactory factory) {
            super(factory);
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

    private final InputStream originalIn = System.in;

    @Test
    void test() {
        String[] inputs = {"1", "test", "true", "", "abc", "1.0", "1.0", "1"};
        InputStream in = new ByteArrayInputStream(String.join(System.lineSeparator(), inputs).getBytes());
        System.setIn(in);
        testCommand.run("test");
        Assertions.assertEquals(0, testCommand.getExitCode());
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
    }
}
