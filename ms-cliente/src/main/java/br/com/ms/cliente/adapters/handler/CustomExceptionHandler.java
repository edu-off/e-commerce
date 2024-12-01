package br.com.ms.cliente.adapters.handler;

import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ClienteException.class)
    public final ResponseEntity<ExceptionResponse> clienteException(ClienteException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(EnderecoException.class)
    public final ResponseEntity<ExceptionResponse> enderecoException(EnderecoException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<ExceptionResponse> noSuchElementExceptionException(NoSuchElementException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), "requisição sem corpo", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponseValidation> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ExceptionResponseValidation exceptionResponseValidation = new ExceptionResponseValidation(getTimestamp(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Existem dados incorretos no corpo da requisição", request.getRequestURI());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> exceptionResponseValidation.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponseValidation);
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

}
