package fr.ancyracademy.esportsclash.auth.application.exception;

import fr.ancyracademy.esportsclash.core.domain.exception.BadRequestException;

public class EmailAddressUnavailableException extends BadRequestException {
    public EmailAddressUnavailableException() {
        super("Email address is already in use.");
    }
}
