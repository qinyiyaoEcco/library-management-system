package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.ScrollRepository;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;

@Component
@CommandLine.Command(name = "scroll-add", mixinStandardHelpOptions = true, description = "Add a new scroll to the virtual library")
public class ScrollAddCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollAddCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--name", description = "Scroll name")
    @RequiredInteractive
    private String name;

    private boolean validateName(String name) {
        return this.scrollRepository.findByName(name) == null;
    }

    @CommandLine.Option(names = "--path", description = "Path to file")
    @RequiredInteractive(invalidMessage = "File not found")
    private File file;

    private boolean validateFile(File file) {
        return file.exists() && file.isFile();
    }

    @Override
    public Integer commandCall() {
        User user = getUser();
        byte[] data;
        try {
            data = Files.readAllBytes(this.file.toPath());
        } catch (Exception e) {
            Application.LOG.error("Unable to read file", e);
            return 1;
        }
        Scroll scroll = new Scroll(this.name, user, data);
        scrollRepository.save(scroll);
        Application.LOG.info("Scroll added successfully with ID {}", scroll.getId());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Add scroll %s", name);
    }

}
