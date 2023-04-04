package com.futuereh.dronefeeder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.futuereh.dronefeeder.repository.StatusDaEntrega;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Entrega {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private LocalDateTime dataHoraRetirada;
  private LocalDateTime dataHoraEntrega;

  private StatusDaEntrega statusDaEntrega;

  private Double latitudeDestino;
  private Double longitudeDestino;

  @ManyToOne
  @JsonIgnore
  private Drone drone;

  public Entrega(
    LocalDateTime dataHoraRetirada,
    LocalDateTime dataHoraEntrega,
    Double latitudeDestino,
    Double longitudeDestino) {
    this.dataHoraRetirada = dataHoraRetirada;
    this.dataHoraEntrega = dataHoraEntrega;

    this.latitudeDestino = latitudeDestino;
    this.longitudeDestino = longitudeDestino;

    this.statusDaEntrega = StatusDaEntrega.PENDENTE;
  }

  public Entrega() {

  }


  public Long getId() {
    return id;
  }

  public LocalDateTime getDataHoraRetirada() {
    return dataHoraRetirada;
  }

  public void setDataHoraRetirada(LocalDateTime dataHoraRetirada) {
    this.dataHoraRetirada = dataHoraRetirada;
  }

  public LocalDateTime getDataHoraEntrega() {
    return dataHoraEntrega;
  }

  public void setDataHoraEntrega(LocalDateTime dataHoraEntrega) {
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
