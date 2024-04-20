package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.BcryptPasswordHahser;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PasswordHasherTests {
    PasswordHasher createPasswordHasher() {
        return new BcryptPasswordHahser();
    }

    @Test
    public void shouldHashPassword() {
        var hasher = createPasswordHasher();
        var clearPassword = "azerty";
        var hashedPassword = hasher.hash(clearPassword);

        Assert.assertTrue(
                hasher.match(clearPassword, hashedPassword)
        );
    }
}
