package lab28.group4.asm2.commands.scroll;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.repositories.ScrollRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Date;
import java.util.List;

@Component
@CommandLine.Command(name = "scroll-search", mixinStandardHelpOptions = true, description = "Search for scrolls based on various criteria")
public class ScrollSearchCommand extends AuthenticatedCommand {

    private final ScrollRepository scrollRepository;

    public ScrollSearchCommand(CommandLine.IFactory factory, ScrollRepository scrollRepository) {
        super(factory);
        this.scrollRepository = scrollRepository;
    }

    @CommandLine.Option(names = "--scroll-id", description = "Search by scroll ID")
    private Long scrollId;

    private boolean validateScrollId(Long scrollId) {
        return scrollId == null || scrollRepository.findById(scrollId).isPresent();
    }

    @CommandLine.Option(names = "--uploader-id", description = "Search by user ID")
    private Long uploaderId;

    private boolean validateUploaderId(Long uploaderId) {
        return uploaderId == null || getUserRepository().findById(uploaderId).isPresent();
    }

    @CommandLine.Option(names = "--name", description = "Search by scroll name")
    private String name;

    @CommandLine.Option(names = "--upload-date", description = "Search by upload date (yyyy-MM-dd)")
    private Date uploadDate;

    @Override
    public Integer commandCall() {
        if (scrollId == null && name == null && uploaderId == null && uploadDate == null) {
            Application.LOG.error("At least one search criteria must be specified!");
            return 1;
        }
        List<Scroll> scrolls = scrollRepository.findScrolls(scrollId, name, uploaderId, uploadDate);
        if (scrolls.isEmpty()) {
            Application.LOG.info("No scrolls found matching the search criteria.");
        } else {
            Application.LOG.info("Scrolls found matching the search criteria:");
            scrolls.forEach(Scroll::print);
        }
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Searched for scrolls";
    }
}
