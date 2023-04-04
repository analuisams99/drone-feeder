package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.service.DroneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  
  @GetMapping 
  public ResponseEntity<List<Drone>> retornarTodosOsDrones() {
    return ResponseEntity.ok().body(droneService.retornarTodosOsDrones());
  }
  
  @GetMapping("/{droneId}")
  public ResponseEntity<Drone> retornarDronePeloId(@PathVariable Long droneId) {
    return ResponseEntity.ok().body(droneService.retornarDronePeloId(droneId));
  }
  
  @PutMapping("/{droneId}")
  public ResponseEntity<Drone> atualizarLocalizacaoDrone(
        @PathVariable Long droneId, 
        @RequestBody double latitude,
        @RequestBody double longitude) {
    return ResponseEntity.ok()
        .body(droneService.atualizarLocalizacaoDrone(droneId, latitude, longitude));
  }
  
  @DeleteMapping("/{droneId}")
  public ResponseEntity<String> deletarDrone(@PathVariable Long droneId) {
    return ResponseEntity.ok().body(droneService.deletarDrone(droneId));
  }
  
  @PostMapping("/{droneId}/entrega")
  public ResponseEntity<Drone> adicionarEntrega(
        @PathVariable Long id,
        @RequestBody Entrega entrega) {
    return ResponseEntity.ok().body(droneService.adicionarEntrega(id, entrega));
  }
}
