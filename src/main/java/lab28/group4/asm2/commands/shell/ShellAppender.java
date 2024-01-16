package lab28.group4.asm2.commands.shell;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.fusesource.jansi.Ansi;

import java.util.concurrent.LinkedBlockingQueue;

public class ShellAppender extends AppenderBase<ILoggingEvent> {
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private Ansi.Color getColorForLevel(Level level) {
        return switch (level.levelInt) {
            case Level.ERROR_INT -> Ansi.Color.RED;
            case Level.WARN_INT -> Ansi.Color.YELLOW;
            case Level.INFO_INT -> Ansi.Color.GREEN;
            case Level.DEBUG_INT -> Ansi.Color.CYAN;
            default -> Ansi.Color.WHITE;
        };
    }

    @Override
    protected void append(ILoggingEvent event) {
        String levelColor = Ansi.ansi().fg(getColorForLevel(event.getLevel())).toString();
        String resetColor = Ansi.ansi().reset().toString();
        String message = String.format("%s[%s] %s%s", levelColor, event.getLevel().levelStr, resetColor, event.getFormattedMessage());
        queue.offer(message);
    }

    public static LinkedBlockingQueue<String> getQueue() {
        return queue;
    }
}
