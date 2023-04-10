package com.futuereh.dronefeeder.repository;

/**Enum statusDaEntrega.*/
public enum StatusDaEntrega {
  PENDENTE("Pendente"),
  EM_ANDAMENTO("Em andamento"),
  ENTREGUE("Entregue"),
  CANCELADO("Cancelado");

  private final String descricao;

  StatusDaEntrega(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
