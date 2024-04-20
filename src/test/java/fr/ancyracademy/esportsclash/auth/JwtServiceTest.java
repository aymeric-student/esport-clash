package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JwtServiceTest {
    @Test
    void shouldTokenizeTheUser() {
        var jwtService = new ConcreteJwtService("sooper_secret_key_please_keep_it_secret_very_important", 60);
        var user = new User("132", "contact@gmail.com", "azerty");
        var token = jwtService.tokenize(user);
        var authUser = jwtService.parse(token);

        Assert.assertNotNull(authUser);
        Assert.assertEquals(user.getId(), authUser.getId());
        Assert.assertEquals(user.getEmailAddress(), authUser.getEmailAddress());
    }
}
