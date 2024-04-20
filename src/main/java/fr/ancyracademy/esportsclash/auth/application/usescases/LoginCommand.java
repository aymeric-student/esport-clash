package fr.ancyracademy.esportsclash.auth.application.usescases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedUserViewModel;

public class LoginCommand implements Command<LoggedUserViewModel> {
    private String emailAddress;
    private String password;

    public LoginCommand() {
    }

    public LoginCommand(String emailAddress, String password) {
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
