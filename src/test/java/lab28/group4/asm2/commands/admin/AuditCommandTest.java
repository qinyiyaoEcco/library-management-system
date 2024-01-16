package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.models.AuditLog;
import lab28.group4.asm2.repositories.AuditLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuditCommandTest {

    @Autowired
    private AuditCommand auditCommand;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Test
    void auditLog() {
        AuditLog auditLog = new AuditLog(null, "test");
        auditLogRepository.save(auditLog);
        String[] inputs = {"audit", "-u", "admin", "-p", "admin"};
        auditCommand.run(inputs);
        Assertions.assertEquals(0, auditCommand.getExitCode());
    }

}
