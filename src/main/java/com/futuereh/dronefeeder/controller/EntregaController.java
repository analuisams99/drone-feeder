package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.service.EntregaService;
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


/**
 * Entrega Controller.
 */
@RestController
@RequestMapping("/entrega")
public class EntregaController {
  @Autowired
  EntregaService entregaService;

  /**
   * Rota para criar Entrega.
   *
   * @param entrega Entrega a ser criada
   * @return Entrega criada
   */
  @PostMapping
  public ResponseEntity<Entrega> create(@RequestBody Entrega entrega) {
    Entrega response = entregaService.create(entrega);

    return ResponseEntity.created(null).body(response);
  }

  /**
   * Rota para editar Entrega.
   *
   * @param id ID da entrega
   * @param entrega Novos dados da entrega
   * @return Entrega editada
   */
  @PutMapping("/{id}")
  public ResponseEntity<Entrega> update(@PathVariable Long id, @RequestBody Entrega entrega) {
    Entrega response = entregaService.update(id, entrega);

    return ResponseEntity.ok(response);
  }

  /**
   * Rota para Listar todas as entregas.
   *
   * @return Lista com todas as Entregas
   */
  @GetMapping
  public ResponseEntity<List<Entrega>> getEntregas() {
    List<Entrega> response = entregaService.findAll();

    return ResponseEntity.ok(response);
  }

  /**
   * Rota para pegar o drone responsável pela entrega.
   *
   * @param id ID da entrega.
   * @return Drone responsável pela entrega
   */
  @GetMapping("/{id}/drone")
  public ResponseEntity<Drone> getDrone(@PathVariable Long id) {
    Drone response = entregaService.findDrone(id);

    return ResponseEntity.ok(response);
  }

  /**
   * Rota para listar uma entrega específica.
   *
   * @param id ID da entrega
   * @return Entrega correspondente
   */
  @GetMapping("/{id}")
  public ResponseEntity<Entrega> getOne(@PathVariable Long id) {
    Entrega response = entregaService.findById(id);

    return ResponseEntity.ok(response);
  }

  /**
   * Rota para deletar uma entrega específica.
   *
   * @param id ID da entrega a ser deletada
   * @return Entrega deletada.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    String response = entregaService.delete(id);

    return ResponseEntity.ok(response);
  }
}
