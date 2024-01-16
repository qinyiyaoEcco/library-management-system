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

import java.io.File;
import java.net.URL;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScrollDownloadCommandTest {

    @Autowired
    private ScrollDownloadCommand scrollDownloadCommand;

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
    void downloadScroll() {
        URL resource = this.getClass().getClassLoader().getResource("scroll");
        String path = resource.getPath() + File.separator + "test.txt";
        String[] args = {"scroll-download", "-u", "admin", "-p", "admin", "--id", "1", "--path", path};
        scrollDownloadCommand.run(args);
        Assertions.assertEquals(0, scrollDownloadCommand.getExitCode());
        File file = new File(path);
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.delete());
    }

    @Test
    void downloadScrollExists() {
        String path = this.getClass().getClassLoader().getResource("scroll/valid.txt").getPath();
        String[] args = {"scroll-download", "-u", "admin", "-p", "admin", "--id", "1", "--path", path};
        scrollDownloadCommand.run(args);
        Assertions.assertEquals(1, scrollDownloadCommand.getExitCode());
    }

}
