package br.com.codebeans.stockapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.codebeans.stockapi.model.dto.FieldErrorDTO;
import br.com.codebeans.stockapi.model.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> methodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        
        List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();

        for (FieldError fe : bindingResult.getFieldErrors()) {
            fieldErrors.add(new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()));
        }

        ResponseDTO<List<FieldErrorDTO>> response = new ResponseDTO<List<FieldErrorDTO>>(
            HttpStatus.BAD_REQUEST.value(),
            "Malformed JSON in request body.",
            fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> entityNotFountException(EntityNotFoundException e) {

        ResponseDTO<List<FieldErrorDTO>> response = new ResponseDTO<List<FieldErrorDTO>>(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage(),
            null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(response);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO<Void>> internalServerError(Throwable t) {
        log.error("Internal server error", t);

        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred on the server. Please try again later.",
            null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(response);
    }

}
