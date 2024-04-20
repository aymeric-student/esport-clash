package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.exception.EmailAddressUnavailableException;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.BcryptPasswordHahser;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usescases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.application.usescases.RegisterCommandHandler;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private final InMemoryUserRepository repository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHahser();

    public RegisterCommandHandler createCommandHandler() {
        return new RegisterCommandHandler(repository, passwordHasher);
    }

    @BeforeEach
    public void setUp() {
        repository.clear();
    }

    @Test
    public void shouldRegister() {
        RegisterCommand command = new RegisterCommand(
                "contact@gmail.com",
                "password"
        );

        var commandHandler = createCommandHandler();
        var response = commandHandler.handle(command);
        var actualUser = repository.findById(response.getId()).get();

        Assert.assertEquals(command.getEmailAddress(), actualUser.getEmailAddress());
        Assert.assertTrue(
                passwordHasher.match(
                        command.getPassword(),
                        actualUser.getPassword()
                )
        );
    }

    @Test
    public void whenEmailIsInUse_shouldThrow() {
        var exestingUser = new User(
                "123",
                "contact@gmail.com",
                "azerty"
        );

        repository.save(exestingUser);
        RegisterCommand command = new RegisterCommand(
                exestingUser.getEmailAddress(),
                "password"
        );

        var commandHandler = createCommandHandler();
        Assert.assertThrows(
                EmailAddressUnavailableException.class,
                () -> commandHandler.handle(command)
        );

    }
}
