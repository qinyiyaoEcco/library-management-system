package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;


@Component
@CommandLine.Command(name = "scroll-update", mixinStandardHelpOptions = true, description = "Update the scroll to the virtual library")
public class ScrollUpdateCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollUpdateCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the scroll to update")
    @RequiredInteractive(invalidMessage = "Invalid scroll ID")
    private Long scrollId;

    private boolean validateScrollId(Long scrollId) {
        Scroll scroll = scrollRepository.findById(scrollId).orElse(null);
        return scroll != null && scroll.getUser().getId().equals(getUser().getId());
    }

    @CommandLine.Option(names = "--new-name", description = "New scroll name")
    private String newName;

    private boolean validateNewName(String newName) {
        return newName == null || scrollRepository.findByName(newName) == null;
    }

    @CommandLine.Option(names = "--path", description = "New path for the scroll")
    private File newPath;

    private boolean validateNewPath(File newPath) {
        return newPath == null || (newPath.exists() && newPath.isFile());
    }

    public Integer commandCall() {
        Scroll scroll = scrollRepository.findById(scrollId).get();
        if (newName != null) {
            scroll.setName(newName);
        }
        if (newPath != null) {
            try {
                byte[] data = Files.readAllBytes(newPath.toPath());
                scroll.setData(data);
            } catch (Exception e) {
                Application.LOG.error("Unable to read file", e);
                return 1;
            }
        }
        scrollRepository.save(scroll);
        Application.LOG.info("Scroll with ID {} updated successfully", scroll.getId());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Update scroll id %d", scrollId);
    }

}
