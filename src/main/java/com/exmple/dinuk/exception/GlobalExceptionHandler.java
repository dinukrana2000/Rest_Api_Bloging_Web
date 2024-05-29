package com.exmple.dinuk.exception;

import com.exmple.dinuk.dto.ErrDetailsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrDetailsDTO> HandleGlobalException(Exception e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(),e.getMessage(),request.getDescription(false));
        errDetailsDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(errDetailsDTO,HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @Override//customizem the error message and send as response
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError) error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomExceptions.UserExistsException.class)
    public ResponseEntity<ErrDetailsDTO> handleUserExistsException(CustomExceptions.UserExistsException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomExceptions.PasswordMismatchException.class)
    public ResponseEntity<ErrDetailsDTO> handlePasswordMismatchException(CustomExceptions.PasswordMismatchException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.EmailNotSentException.class)
    public ResponseEntity<ErrDetailsDTO> handleEmailNotSentException(CustomExceptions.EmailNotSentException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomExceptions.UserNotVerifiedException.class)
    public ResponseEntity<ErrDetailsDTO> handleUserNotVerifiedException(CustomExceptions.UserNotVerifiedException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomExceptions.InvalidPasswordException.class)
    public ResponseEntity<ErrDetailsDTO> handleInvalidPasswordException(CustomExceptions.InvalidPasswordException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.UserDoesNotExistException.class)
    public ResponseEntity<ErrDetailsDTO> handleUserDoesNotExistException(CustomExceptions.UserDoesNotExistException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExceptions.InvalidOTPException.class)
    public ResponseEntity<ErrDetailsDTO> handleInvalidOTPException(CustomExceptions.InvalidOTPException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.InvalidJwtTokenException.class)
    public ResponseEntity<ErrDetailsDTO> handleInvalidJwtTokenException(CustomExceptions.InvalidJwtTokenException e, WebRequest request) {
        ErrDetailsDTO errDetailsDTO = new ErrDetailsDTO(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errDetailsDTO, HttpStatus.UNAUTHORIZED);
    }

}
