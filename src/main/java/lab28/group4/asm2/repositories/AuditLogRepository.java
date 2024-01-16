package lab28.group4.asm2.repositories;

import lab28.group4.asm2.models.AuditLog;
import org.springframework.data.repository.ListCrudRepository;

public interface AuditLogRepository extends ListCrudRepository<AuditLog, Long> {
}
