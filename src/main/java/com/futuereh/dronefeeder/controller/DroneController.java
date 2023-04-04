package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**Classe DroneController.*/
@RestController
@RequestMapping("/drones")
public class DroneController {

  @Autowired
  private DroneService droneService;

}
