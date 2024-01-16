package lab28.group4.asm2.commands.user;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.ApplicationCommand;
import lab28.group4.asm2.commands.RequiredInteractive;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "register", mixinStandardHelpOptions = true, description = "Register")
public class UserRegisterCommand extends ApplicationCommand {

    private final UserRepository userRepository;

    public UserRegisterCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(factory);
        this.userRepository = userRepository;
    }

    @CommandLine.Option(names = "--username", description = "Username")
    @RequiredInteractive(invalidMessage = "Username already exists")
    private String username;

    private boolean validateUsername(String username) {
        return userRepository.findByUsername(username) == null;
    }

    @CommandLine.Option(names = "--password", description = "Password")
    @RequiredInteractive
    private String password;

    @CommandLine.Option(names = "--full-name", description = "Full name")
    @RequiredInteractive
    private String fullName;

    @CommandLine.Option(names = "--email", description = "Email")
    @RequiredInteractive
    private String email;

    @CommandLine.Option(names = "--phone", description = "Phone number")
    @RequiredInteractive
    private String phone;

    @Override
    public Integer commandCall() {
        User user = new User(username, password, Role.USER, phone, email, fullName);
        userRepository.save(user);
        Application.LOG.info("User registered successfully");
        return 0;
    }

    @Override
    public String getAuditLog() {
        return "Register user " + username;
    }

}
