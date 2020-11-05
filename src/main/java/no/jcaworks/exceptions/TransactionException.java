package no.jcaworks.exceptions;

import lombok.Getter;

import java.util.UUID;

public class TransactionException extends RuntimeException {
    @Getter
    private final UUID transactionId;

    public TransactionException(UUID transactionId, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.transactionId = transactionId;
    }
}
