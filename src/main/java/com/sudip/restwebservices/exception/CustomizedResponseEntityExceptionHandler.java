package com.sudip.restwebservices.exception;

import com.sudip.restwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice // Applicable to all controllers
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // For all the exceptions same pattern
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // For user not found exception
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle Validation on username and birthDate on POST request
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
               "Total Errors: "+  ex.getErrorCount() + " First Error: " + ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    }

    /*
    This was the message previous:
    "message": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Object> " +
            "com.sudip.restwebservices.user.UserResource.createUser(com.sudip.restwebservices.user.User): [Field error in " +
            "object 'user' on field 'birthDate': rejected value [2040-01-22]; codes [Past.user.birthDate,Past.birthDate," +
            "Past.java.time.LocalDate,Past]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: " +
            "codes [user.birthDate,birthDate]; arguments []; default message [birthDate]];" +
            " default message [must be a past date] ",

            *
            After implementing Validation:
            {
                "timestamp": "2024-01-22T10:31:44.4047594",
                "message": "Total Errors: 1First Error: Birth date should be past date",
                "details": "uri=/users"
            }
l
     */


