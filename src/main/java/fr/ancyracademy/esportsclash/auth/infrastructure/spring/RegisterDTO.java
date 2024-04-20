package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

public class RegisterDTO {
    private String emailAddress;
    private String password;

    public RegisterDTO(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public RegisterDTO() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
