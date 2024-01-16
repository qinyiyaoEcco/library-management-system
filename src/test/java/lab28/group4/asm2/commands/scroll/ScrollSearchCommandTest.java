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
public class ScrollSearchCommandTest {
    @Autowired
    private ScrollSearchCommand scrollSearchCommand;

    @Autowired
    private ScrollRepository scrollRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void addScroll() {
        Scroll scroll = new Scroll("test", userRepository.findByUsername("admin"), "test data".getBytes());
        scrollRepository.save(scroll);
    }

    @Test
    void searchName() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin", "--name", "te"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(0, scrollSearchCommand.getExitCode());
    }

    @Test
    void searchUser() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin", "--uploader-id", "1"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(0, scrollSearchCommand.getExitCode());
    }

    @Test
    void searchScrollId() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin", "--scroll-id", "1"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(0, scrollSearchCommand.getExitCode());
    }

    @Test
    void searchDate() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin", "--upload-date", "2021-05-01"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(0, scrollSearchCommand.getExitCode());
    }

    @Test
    void searchAll() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin", "--name", "te", "--uploader-id", "1", "--upload-date", "2021-05-01"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(0, scrollSearchCommand.getExitCode());
    }

    @Test
    void searchNothing() {
        String[] args = {"scroll-search", "-u", "admin", "-p", "admin"};
        scrollSearchCommand.run(args);
        Assertions.assertEquals(1, scrollSearchCommand.getExitCode());
    }

}
