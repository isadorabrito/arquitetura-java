package br.edu.infnet.isadoraapi.exceptions;

public class InvalidDonationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidDonationException(String message) {
        super(message);
    }
}