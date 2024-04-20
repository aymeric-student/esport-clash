package fr.ancyracademy.esportsclash.auth.application.usescases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedUserViewModel;
import fr.ancyracademy.esportsclash.core.domain.exception.BadRequestException;
import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedUserViewModel> {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;


    public LoginCommandHandler(UserRepository userRepository, JwtService jwtService, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoggedUserViewModel handle(LoginCommand loginCommand) {
        var user = this.userRepository
                .findByEmailAddress(loginCommand.getEmailAddress())
                .orElseThrow(() -> new NotFoundException("User", ""));
        var match = this.passwordHasher.match(loginCommand.getPassword(), user.getPassword());

        if (!match) {
            throw new BadRequestException("Invalid password");
        }
        
        var token = this.jwtService.tokenize(user);
        return new LoggedUserViewModel(user.getId(), user.getEmailAddress(), token);
    }
}
