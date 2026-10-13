package com.readmee.readme.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.readmee.readme.models.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Hai inviato un body con elementi non validi");

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "L'elemento cercato non esiste nel database");

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(
                        Exception ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Errore interno del server:" + ex.getMessage());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}
