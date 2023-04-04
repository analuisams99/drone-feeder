package com.futuereh.dronefeeder.repository;

import com.futuereh.dronefeeder.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de Entrega.
 */
@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {}
