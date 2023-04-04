package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**Classe de serviço DroneService.*/
@Service
public class DroneService {
  @Autowired
  private DroneRepository repository;
  
  /**Método de inserir novo drone.*/
  public Drone inserirDrone(Drone drone) {
    return repository.save(drone);
  }
  
  /**Método de retornar todos os drones.*/
  public List<Drone> retornarTodosOsDrones() {
    return repository.findAll();
  }

  /**Método de retornar drone pelo id.*/
  public Optional<Drone> retornarDronePeloId(Long id) {
    return repository.findById(id);
  }

}
