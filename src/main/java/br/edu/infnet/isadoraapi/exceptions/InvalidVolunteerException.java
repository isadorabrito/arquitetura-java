package br.edu.infnet.isadoraapi.exceptions;

public class InvalidVolunteerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidVolunteerException(String mensagem) {
		super(mensagem);
	}
}