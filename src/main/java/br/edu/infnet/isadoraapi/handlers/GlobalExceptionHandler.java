package br.edu.infnet.isadoraapi.handlers;

import br.edu.infnet.isadoraapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        NotFoundDonorException.class,
        NotFoundVolunteerException.class,
        NotFoundDonationException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        InvalidDonationException.class,
        InvalidVolunteerException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldError()
            .getDefaultMessage();
        return createErrorResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return createErrorResponse("Ocorreu um erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(
            new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
            ),
            status
        );
    }
}

record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message
) {}