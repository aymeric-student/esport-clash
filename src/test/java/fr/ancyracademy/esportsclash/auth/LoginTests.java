package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.BcryptPasswordHahser;
import fr.ancyracademy.esportsclash.auth.application.usescases.LoginCommand;
import fr.ancyracademy.esportsclash.auth.application.usescases.LoginCommandHandler;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedUserViewModel;
import fr.ancyracademy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.core.domain.exception.BadRequestException;
import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LoginTests {
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final ConcreteJwtService jwtService = new ConcreteJwtService(
            "sooper_secret_key_please_keep_it_secret_very_important",
            60);
    private final User user = new User(
            "132",
            "contact@gmail.com",
            new BcryptPasswordHahser().hash("azerty"));


    LoginCommandHandler createLoginCommandHandler() {
        return new LoginCommandHandler(userRepository, jwtService, new BcryptPasswordHahser());
    }

    @BeforeEach
    void setUp() {
        userRepository.clear();
        userRepository.save(user);

    }

    // mot de passe et email correct
    @Nested
    class HappyPath {
        @Test
        void shouldReturnUser() {


            var command = new LoginCommand(
                    "contact@gmail.com",
                    "azerty");

            var commandHandler = createLoginCommandHandler();

            LoggedUserViewModel result = commandHandler.handle(command);

            Assert.assertNotNull(result);

            Assert.assertEquals(
                    user.getId(),
                    result.getId()
            );

            Assert.assertEquals(
                    user.getEmailAddress(),
                    result.getEmailAddress()
            );

            var authentificationToken = jwtService.parse(result.getToken());

            Assert.assertEquals(
                    user.getId(),
                    authentificationToken.getId()
            );

            Assert.assertEquals(
                    user.getEmailAddress(),
                    authentificationToken.getEmailAddress()
            );

        }
        // le token est généré
    }

    // addresse email est incorrect
    @Nested
    class Scenario_TheEmailAddressIsIncorrect {
        @Test
        void shouldThrowNotFound() {


            var command = new LoginCommand(
                    "johndoe@gmail.com",
                    "password");

            var commandHandler = createLoginCommandHandler();

            Assert.assertThrows(
                    NotFoundException.class,
                    () -> commandHandler.handle(command)
            );
        }
    }

    // mot de passe est incorrect
    @Nested
    class Scenario_TheEmailPasswodIsIncorrect {
        @Test
        void shouldThrowNotFoundForPassword() {


            var command = new LoginCommand(
                    "contact@gmail.com",
                    "not_the_right_password");

            var commandHandler = createLoginCommandHandler();

            Assert.assertThrows(
                    BadRequestException.class,
                    () -> commandHandler.handle(command)
            );
        }
    }
}
