package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

public class LoginDto {
    private String emailAddress;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
