package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@CommandLine.Command(name = "scroll-download", mixinStandardHelpOptions = true, description = "Download scroll from the virtual library")
public class ScrollDownloadCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollDownloadCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the scroll to download")
    @RequiredInteractive(invalidMessage = "Invalid scroll ID")
    private Long id;

    private boolean validateId(Long id) {
        return this.scrollRepository.findById(id).isPresent();
    }

    @CommandLine.Option(names = "--path", description = "File path to download the scroll to")
    @RequiredInteractive(invalidMessage = "Invalid path!")
    private File path;

    private boolean validatePath(File path) {
        return !path.isDirectory();
    }

    @CommandLine.Option(names = "--overwrite", description = "Overwrite file if it exists")
    private boolean overwrite;

    @Override
    public Integer commandCall() {
        if (path.exists() && !overwrite) {
            Application.LOG.error("File already exists! Use flag --overwrite to overwrite the file");
            return 1;
        }
        Scroll scroll = this.scrollRepository.findById(id).get();
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(scroll.getData());
            fos.flush();
        } catch (IOException e) {
            Application.LOG.error("Failed to write to file: {}", e.getMessage());
            return 1;
        }
        Application.LOG.info("Successfully downloaded scroll {} to {}", scroll.getName(), path.getAbsolutePath());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Downloaded scroll %d", id);
    }
}