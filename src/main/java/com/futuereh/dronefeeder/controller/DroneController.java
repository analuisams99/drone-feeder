package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**Classe DroneController.*/
@RestController
@RequestMapping("/drones")
public class DroneController {

  @Autowired
  private DroneService droneService;

  @PostMapping
  public ResponseEntity<Drone> inserirDrone(@RequestBody Drone drone) {
    return ResponseEntity.ok().body(droneService.inserirDrone(drone));
  }
}
