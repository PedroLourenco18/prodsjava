package br.com.pedroloureco.prodsjava.controller;

import br.com.pedroloureco.prodsjava.exception.ElementNotFoundException;
import br.com.pedroloureco.prodsjava.exception.EmailAlreadyExistsException;
import br.com.pedroloureco.prodsjava.domain.reponse.BasicResponse;
import br.com.pedroloureco.prodsjava.domain.reponse.FieldsErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<BasicResponse> handleElementNotFoundException(
            ElementNotFoundException e){
        BasicResponse response = new BasicResponse(
                true,
                e.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldsErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        var fields = fieldErrors
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        fieldError ->
                                fieldError.getDefaultMessage() == null ? "" : fieldError.getDefaultMessage()
                ));

        FieldsErrorResponse response = new FieldsErrorResponse(
                true,
                "Invalid Fields",
                fields
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<BasicResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        BasicResponse response = new BasicResponse(
                true,
                e.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }
}
