package com.futuereh.dronefeeder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.futuereh.dronefeeder.repository.StatusDaEntrega;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Model de Entregas.
 */
@Entity
public class Entrega {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String dataHoraRetirada;
  private String dataHoraEntrega;

  private StatusDaEntrega statusDaEntrega = StatusDaEntrega.PENDENTE;

  private Double latitudeDestino;
  private Double longitudeDestino;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "drone_id")
  private Drone drone;

  /**
   * Construtor do Model de Entregas.
   *
   * @param latitudeDestino Latitude do endereço de destino
   * @param longitudeDestino Longitude do endereço de destino
   */
  public Entrega(
      Double latitudeDestino,
      Double longitudeDestino
  ) {
    this.latitudeDestino = latitudeDestino;
    this.longitudeDestino = longitudeDestino;
  }

  public Entrega() {

  }

  public Long getId() {
    return id;
  }

  public String getDataHoraRetirada() {
    return dataHoraRetirada;
  }

  public void setDataHoraRetirada(String dataHoraRetirada) {
    this.dataHoraRetirada = dataHoraRetirada;
  }

  public String getDataHoraEntrega() {
    return dataHoraEntrega; 
  }

  public void setDataHoraEntrega(String dataHoraEntrega) {
    this.dataHoraEntrega = dataHoraEntrega;
  }

  public StatusDaEntrega getStatusDaEntrega() {
    return statusDaEntrega;
  }

  public void setStatusDaEntrega(StatusDaEntrega statusDaEntrega) {
    this.statusDaEntrega = statusDaEntrega;
  }

  public Double getLatitudeDestino() {
    return latitudeDestino;
  }

  public void setLatitudeDestino(Double latitudeDestino) {
    this.latitudeDestino = latitudeDestino;
  }

  public Double getLongitudeDestino() {
    return longitudeDestino;
  }

  public void setLongitudeDestino(Double longitudeDestino) {
    this.longitudeDestino = longitudeDestino;
  }

  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
  }
}
