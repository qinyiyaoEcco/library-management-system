package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import lab28.group4.asm2.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScrollUpdateCommandTest {

    @Autowired
    private ScrollUpdateCommand scrollUpdateCommand;

    @Autowired
    private ScrollRepository scrollRepository;

    @Autowired
    private UserRepository userRepository;

    private String getResourcePath(String filename) {
        return this.getClass().getClassLoader().getResource("scroll/" + filename).getPath();
    }

    @BeforeEach
    void addScroll() {
        Scroll scroll = new Scroll("test", userRepository.findByUsername("admin"), "test data".getBytes());
        scrollRepository.save(scroll);
    }

    @Test
    void updateScrollName() {
        String[] args = {"scroll-update", "-u", "admin", "-p", "admin", "--id", "1", "--new-name", "updated test"};
        scrollUpdateCommand.run(args);
        Assertions.assertEquals(0, scrollUpdateCommand.getExitCode());

    }

    @Test
    void updateScrollData() {
        String[] args = {"scroll-update", "-u", "admin", "-p", "admin", "--id", "1", "--path", getResourcePath("valid.txt")};
        scrollUpdateCommand.run(args);
        Assertions.assertEquals(0, scrollUpdateCommand.getExitCode());
    }

}
