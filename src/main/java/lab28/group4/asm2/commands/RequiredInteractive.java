package lab28.group4.asm2.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredInteractive {
    String invalidMessage() default "Invalid input";
}
