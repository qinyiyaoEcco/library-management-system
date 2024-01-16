package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.models.User;
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
public class ScrollViewCommandTest {

    @Autowired
    private ScrollViewCommand scrollViewCommand;

    @Autowired
    private ScrollRepository scrollRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void previewScrolls() {
        User user = userRepository.findByUsername("admin");
        Scroll scroll1 = new Scroll("test1", user, "test data 1".getBytes());
        Scroll scroll2 = new Scroll("test2", user, "test data 2".getBytes());
        scrollRepository.save(scroll1);
        scrollRepository.save(scroll2);
        String[] args = {"scroll-view", "-u", "admin", "-p", "admin"};
        scrollViewCommand.run(args);
        Assertions.assertEquals(0, scrollViewCommand.getExitCode());
    }

    @Test
    void previewNoScrolls() {
        String[] args = {"scroll-view", "-u", "admin", "-p", "admin"};
        scrollViewCommand.run(args);
        Assertions.assertEquals(1, scrollViewCommand.getExitCode());
    }
}
