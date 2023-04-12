package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.exceptions.BadRequestException;
import com.futuereh.dronefeeder.exceptions.ErrorResponse;
import com.futuereh.dronefeeder.exceptions.NaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Controller.
 */
@ControllerAdvice
public class ExceptionController {
  /**
   * Not Found Error.
   *
   * @param exception NaoEncontrada
   * @return Mensagem de erro
   */
  @ExceptionHandler(NaoEncontradoException.class)
  public ResponseEntity<ErrorResponse> notFound(RuntimeException exception) {
    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> badRequest(RuntimeException exception) {
    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * Internal Error.
   *
   * @param exception internalError
   * @return Mensagem de erro
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> internalError(RuntimeException exception) {
    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
