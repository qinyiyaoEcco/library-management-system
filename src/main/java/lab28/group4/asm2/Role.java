package lab28.group4.asm2;

import org.fusesource.jansi.Ansi;

public enum Role {
    USER(Ansi.ansi().fgBrightCyan().toString()), ADMIN(Ansi.ansi().fgBrightRed().toString());

    Role(String color) {
        this.color = color;
    }

    public final String color;
}
