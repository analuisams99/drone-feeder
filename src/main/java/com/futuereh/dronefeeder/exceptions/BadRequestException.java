package com.futuereh.dronefeeder.exceptions;

/**
 * Exceção BadRequest.
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException{
  public BadRequestException(String message) {
    super(message);
  }
}
