package fr.ancyracademy.esportsclash.auth.application.usescases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.application.exception.EmailAddressUnavailableException;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public IdResponse handle(RegisterCommand command) {
        var isEmailAddressAvailable = userRepository.isEmailAddressAvailable(command.getEmailAddress());

        if (!isEmailAddressAvailable) {
            throw new EmailAddressUnavailableException();
        }

        var User = new User(
                UUID.randomUUID().toString(),
                command.getEmailAddress(),
                passwordHasher.hash(command.getPassword())
        );
        userRepository.save(User);
        return new IdResponse(User.getId());
    }
}
