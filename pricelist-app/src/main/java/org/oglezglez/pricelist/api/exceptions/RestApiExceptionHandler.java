package org.oglezglez.pricelist.api.exceptions;

import org.oglezglez.pricelist.model.v1.Error;
import org.oglezglez.pricelist.error.PricelistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {PricelistNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> notFoundException(Throwable ex, WebRequest req) {
        Error error = (new Error()).code(HttpStatus.NOT_FOUND.value()).message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(error);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> conversionException(Throwable ex, WebRequest req) {
        Error error = (new Error()).code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(error);
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> unknownException(Throwable ex, WebRequest req) {
        Error error = (new Error()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(error);
    }

}
