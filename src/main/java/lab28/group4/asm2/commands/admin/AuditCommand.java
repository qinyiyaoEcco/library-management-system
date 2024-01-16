package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.AuditLog;
import lab28.group4.asm2.repositories.AuditLogRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "audit", description = "Audit log", mixinStandardHelpOptions = true)
public class AuditCommand extends AdminCommand {

    private final AuditLogRepository auditLogRepository;

    public AuditCommand(CommandLine.IFactory factory, AuditLogRepository auditLogRepository) {
        super(factory);
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public Integer commandCall() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        for (AuditLog auditLog : auditLogs) {
            Application.LOG.info("{} {}: {}", auditLog.getCreatedAt(), auditLog.getUser() != null ? auditLog.getUser().getUsername() : "guest", auditLog.getAction());
        }
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "View audit log";
    }

}
