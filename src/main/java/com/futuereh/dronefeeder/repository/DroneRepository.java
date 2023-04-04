package com.futuereh.dronefeeder.repository;

import com.futuereh.dronefeeder.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**(Repository) Interface DroneRepository.*/
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {}
