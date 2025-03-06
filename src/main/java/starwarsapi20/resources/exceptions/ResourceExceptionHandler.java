package starwarsapi20.resources.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import starwarsapi20.services.exceptions.DataBaseException;
import starwarsapi20.services.exceptions.PlanetaInvalidoException;
import starwarsapi20.services.exceptions.ResourceNotFoundException;
import starwarsapi20.services.exceptions.TraidorException;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    //tratamento manual para erros mais comuns
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TraidorException.class)
    public ResponseEntity<StandardError> traidor(TraidorException e, HttpServletRequest request) {
        String error = "Traidor não pode marcar localização";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PlanetaInvalidoException.class)
    public ResponseEntity<StandardError> planetaInvalido(PlanetaInvalidoException e, HttpServletRequest request) {
        String error = "Planeta inválido";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
