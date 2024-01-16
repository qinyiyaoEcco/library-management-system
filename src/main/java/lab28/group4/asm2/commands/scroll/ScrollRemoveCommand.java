package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "scroll-remove", mixinStandardHelpOptions = true, description = "Remove a scroll from the virtual library")
public class ScrollRemoveCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollRemoveCommand(ScrollRepository scrollRepository, CommandLine.IFactory factory) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--id", description = "Scroll ID")
    @RequiredInteractive(invalidMessage = "Invalid scroll ID")
    private Long scrollId;

    private boolean validateScrollId(Long scrollId) {
        Scroll scroll = scrollRepository.findById(scrollId).orElse(null);
        return scroll != null && scroll.getUser().getId().equals(getUser().getId());
    }

    @Override
    public Integer commandCall() {
        Scroll scroll = scrollRepository.findById(scrollId).get();
        scrollRepository.delete(scroll);
        Application.LOG.info("Scroll deleted successfully with ID: {}", scroll.getId());
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Remove scroll id %d", scrollId);
    }
}





