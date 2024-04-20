package fr.ancyracademy.esportsclash.auth.application.usescases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

public class RegisterCommand implements Command<IdResponse> {
    private String emailAddress;
    private String password;

    public RegisterCommand(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public RegisterCommand() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
