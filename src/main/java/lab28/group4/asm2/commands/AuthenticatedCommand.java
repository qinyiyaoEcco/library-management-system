package lab28.group4.asm2.commands;

import lab28.group4.asm2.Application;
import lab28.group4.asm2.Role;
import lab28.group4.asm2.Session;
import lab28.group4.asm2.models.User;
import lab28.group4.asm2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

public abstract class AuthenticatedCommand extends ApplicationCommand {

    private final Role role;
    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CommandLine.Option(names = {"-u", "--username"}, description = "Username")
    private String username;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password")
    private String password;

    private User user;

    public AuthenticatedCommand(Role role, CommandLine.IFactory factory) {
        super(factory);
        this.role = role;
    }

    public AuthenticatedCommand(CommandLine.IFactory factory) {
        super(factory);
        this.role = Role.USER;
    }

    @Override
    public Integer call() {
        if (this.username == null || this.password == null) {
            this.user = Session.getUser();
        } else {
            this.user = userRepository.findByUsername(this.username);
            if (this.user == null || !user.validatePassword(this.password)) {
                Application.LOG.error("Invalid username or password");
                return 1;
            }
        }
        if (this.user == null) {
            Application.LOG.error("You must login first or provide username and password");
            return 1;
        }
        if (this.user.getRole().ordinal() < this.role.ordinal()) {
            Application.LOG.error("You don't have permission to execute this command");
            return 1;
        }
        if (isInjectOptionsFailed()) {
            return 1;
        }
        int status = commandCall();
        if (status == 0) {
            saveAudit();
        }
        return status;
    }

    protected User getUser() {
        return user;
    }

    protected UserRepository getUserRepository() {
        return this.userRepository;
    }

}
