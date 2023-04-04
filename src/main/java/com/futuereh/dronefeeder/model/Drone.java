package com.futuereh.dronefeeder.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**Classe de entidade Drone.*/
@Entity
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String modelo;

  private double latitudeAtual;

  private double longitudeAtual;

  @OneToMany(mappedBy = "drone")
  private List<Entrega> entregas;
}
