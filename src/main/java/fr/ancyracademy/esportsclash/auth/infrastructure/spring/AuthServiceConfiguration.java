package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.BcryptPasswordHahser;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServiceConfiguration {
    @Bean
    public PasswordHasher passwordHasher() {
        return new BcryptPasswordHahser();
    }

    @Bean
    public JwtService jwtService() {
        return new ConcreteJwtService(
                "supersecretkeypleasedonttellanyone",
                3600
        );
    }
}
