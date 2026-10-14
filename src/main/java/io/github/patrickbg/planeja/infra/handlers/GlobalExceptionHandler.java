package io.github.patrickbg.planeja.infra.handlers;

import io.github.patrickbg.planeja.common.exceptions.ValidationException;
import io.github.patrickbg.planeja.common.validation.CampoInvalido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e){
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", e.getCamposInvalidos());

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        var camposInvalidos = e.getFieldErrors().stream().map(
                fieldError -> new CampoInvalido(fieldError.getField(), fieldError.getDefaultMessage())
        ).toList();

        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", camposInvalidos);

        return ResponseEntity.status(status).body(body);
    }
}
