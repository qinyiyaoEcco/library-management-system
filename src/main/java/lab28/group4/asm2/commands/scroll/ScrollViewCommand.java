package lab28.group4.asm2.commands.scroll;


import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "scroll-view", mixinStandardHelpOptions = true, description = "Preview list of scrolls in the virtual library")
public class ScrollViewCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollViewCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @Override
    public Integer commandCall() {
        List<Scroll> scrolls = this.scrollRepository.findAll();

        if (scrolls.isEmpty()) {
            Application.LOG.info("No scrolls available in the library.");
            return 1;
        }

        Application.LOG.info("Scrolls Preview:");
        for (Scroll scroll : scrolls) {
            Application.LOG.info("ID: {}, Name: {}", scroll.getId(), scroll.getName());
        }

        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Previewed all scrolls.";
    }
}
