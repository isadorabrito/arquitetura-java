package br.edu.infnet.isadoraapi.exceptions;

public class NotFoundVolunteerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundVolunteerException(String mensagem) {
        super(mensagem);
    }
}