package lab28.group4.asm2.commands.admin;

import lab28.group4.asm2.Role;
import lab28.group4.asm2.commands.AuthenticatedCommand;
import lab28.group4.asm2.repositories.UserRepository;
import picocli.CommandLine;

abstract public class AdminCommand extends AuthenticatedCommand {
    public AdminCommand(CommandLine.IFactory factory) {
        super(Role.ADMIN, factory);
    }
}
