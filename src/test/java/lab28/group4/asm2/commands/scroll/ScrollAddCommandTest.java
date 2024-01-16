package lab28.group4.asm2.commands.scroll;

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
public class ScrollAddCommandTest {

    @Autowired
    private ScrollAddCommand scrollAddCommand;

    private String getResourcePath(String filename) {
        return this.getClass().getClassLoader().getResource("scroll/" + filename).getPath();
    }

    @Test
    void addScroll() {
        String[] args = {"scroll-add", "-u", "admin", "-p", "admin", "--name", "test", "--path", getResourcePath("valid.txt")};
        scrollAddCommand.run(args);
        Assertions.assertEquals(0, scrollAddCommand.getExitCode());
    }

}
