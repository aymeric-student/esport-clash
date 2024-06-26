package fr.ancyracademy.esportsclash.core.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity) {
        super(
                String.format(
                        "%s not found",
                        entity
                )
        );
    }

    public NotFoundException(String entity, String key) {
        super(String.format("%s with the key %s not found", entity, key));
    }
}
