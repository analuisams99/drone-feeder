package com.futuereh.dronefeeder.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**Classe de entidade Drone.*/
@Entity
@Table(name = "drone")
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String modelo;

  private double latitudeAtual;

  private double longitudeAtual;

  @Column
  @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
  private List<Entrega> entregas = new ArrayList<>();

  public Drone() {}
  
  /**Método construtor. */
  public Drone(String modelo, double latitudeAtual, double longitudeAtual) {
    this.modelo = modelo;
    this.latitudeAtual = latitudeAtual;
    this.longitudeAtual = longitudeAtual;
  }

  public Long getId() {
    return id;
  }
  
  public String getModelo() {
    return modelo;
  }
  
  public void setModelo(String modelo) {
    this.modelo = modelo;
  }
  
  public double getLatitudeAtual() {
    return latitudeAtual;
  }
  
  public void setLatitudeAtual(double latitudeAtual) {
    this.latitudeAtual = latitudeAtual;
  }
  
  public double getLongitudeAtual() {
    return longitudeAtual;
  }
  
  public void setLongitudeAtual(double longitudeAtual) {
    this.longitudeAtual = longitudeAtual;
  }
  
  public List<Entrega> getEntregas() {
    return entregas;
  }
  
  public void adicionarEntrega(Entrega entrega) {
    this.entregas.add(entrega);
  }
    
}
