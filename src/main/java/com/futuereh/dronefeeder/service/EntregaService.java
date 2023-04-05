package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.exceptions.NaoEncontradoException;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service de Entrega.
 */
@Service
public class EntregaService {
  @Autowired
  private EntregaRepository entregaRepo;

  public Entrega create(Entrega entrega) {
    return entregaRepo.save(entrega);
  }

  public List<Entrega> findAll() {
    return entregaRepo.findAll();
  }

  /**
   * Retorna a Entrega correspondente ao ID.
   *
   * @param id ID da Entrega
   * @return Entrega Correspondente
   */
  public Entrega findById(Long id) {
    Entrega entrega = entregaRepo.findById(id).get();

    if (!entregaRepo.existsById(entrega.getId())) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }

    return entrega;
  }

  /**
   * Retorna Drone que está com a entrega correspondente.
   *
   * @param id ID da Entrega
   * @return Drone Correspondente
   */
  public Drone findDrone(Long id) {
    Entrega entrega = entregaRepo.findById(id).get();

    if (!entregaRepo.existsById(entrega.getId())) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }

    return entrega.getDrone();
  }

  /**
   * Atualiza informações da entrega.
   *
   * @param id ID da Entrega
   * @param entrega Novos dados da Entrega
   * @return Entrega atualizada
   */
  public Entrega update(Long id, Entrega entrega) {
    Entrega entregaParaAtualizar = entregaRepo.findById(id).get();

    if (!entregaRepo.existsById(entregaParaAtualizar.getId())) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }

    entregaParaAtualizar.setDataHoraEntrega(entrega.getDataHoraEntrega());
    entregaParaAtualizar.setDataHoraRetirada(entrega.getDataHoraRetirada());
    entregaParaAtualizar.setDrone(entrega.getDrone());
    entregaParaAtualizar.setLatitudeDestino(entrega.getLatitudeDestino());
    entregaParaAtualizar.setLongitudeDestino(entrega.getLongitudeDestino());

    return entregaParaAtualizar;
  }

  /**
   * Deleta a entrega correspondente.
   *
   * @param id ID da entrega
   * @return Entrega deletada
   */
  public Entrega delete(Long id) {
    Entrega entregaParaDeletar = entregaRepo.findById(id).get();

    if (!entregaRepo.existsById(entregaParaDeletar.getId())) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }

    entregaRepo.deleteById(id);

    return entregaParaDeletar;
  }
}