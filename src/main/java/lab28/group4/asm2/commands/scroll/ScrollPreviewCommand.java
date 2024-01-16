package lab28.group4.asm2.commands.scroll;


import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "scroll-preview", mixinStandardHelpOptions = true, description = "View details of a specific scroll in the virtual library")
public class ScrollPreviewCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollPreviewCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--id", description = "Scroll ID")
    @RequiredInteractive(invalidMessage = "Invalid scroll ID")
    private Long id;

    private boolean validateId(Long id) {
        return this.scrollRepository.findById(id).isPresent();
    }

    @Override
    public Integer commandCall() {
        Scroll scroll = this.scrollRepository.findById(id).get();
        scroll.print();
        return 0;
    }

    @Override
    public String getAuditLog() {
        return String.format("Previewed scroll %d", id);
    }
}
