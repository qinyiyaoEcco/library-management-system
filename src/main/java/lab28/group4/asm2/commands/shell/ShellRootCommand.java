package lab28.group4.asm2.commands.shell;

import lab28.group4.asm2.commands.ApplicationCommand;
import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.List;

@Component
@CommandLine.Command
@ShellExclude
public class ShellRootCommand extends ApplicationCommand {
    PrintWriter out;

    public ShellRootCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @Autowired
    @Lazy
    public void setCommands(List<ApplicationCommand> commands) {
        CommandLine commandLine = getCommandLine();
        for (ApplicationCommand command : commands) {
            if (command.getClass().getAnnotation(ShellExclude.class) != null) {
                continue;
            }
            commandLine.addSubcommand(command);
        }
    }

    @Override
    public Integer commandCall() {
        out.println(this.getCommandLine().getUsageMessage());
        return 0;
    }

    public void setReader(LineReader reader) {
        this.out = reader.getTerminal().writer();
    }

    @Override
    public String getAuditLog() {
        return null;
    }
}
