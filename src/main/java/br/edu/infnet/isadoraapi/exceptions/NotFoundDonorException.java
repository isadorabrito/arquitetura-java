package br.edu.infnet.isadoraapi.exceptions;

public class NotFoundDonorException extends RuntimeException {
    public NotFoundDonorException(String message) {
        super(message);
    }
}