package fr.ancyracademy.esportsclash.auth.application.services.passwordHasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHahser implements PasswordHasher {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean match(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
