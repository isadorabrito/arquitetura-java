package br.edu.infnet.isadoraapi.exceptions;

public class NotFoundDonationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotFoundDonationException(String message) {
        super(message);
    }
}