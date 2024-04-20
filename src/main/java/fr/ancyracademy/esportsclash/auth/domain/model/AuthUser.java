package fr.ancyracademy.esportsclash.auth.domain.model;

public class AuthUser {
    private final String emailAddress;
    private final String id;

    public AuthUser(String id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getId() {
        return id;
    }
}
