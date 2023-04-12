package com.futuereh.dronefeeder.exceptions;

/**Exceção de não encontrado.*/
@SuppressWarnings("serial")
public class NaoEncontradoException extends RuntimeException {

  public NaoEncontradoException(String message) {
    super(message);
  }
}
