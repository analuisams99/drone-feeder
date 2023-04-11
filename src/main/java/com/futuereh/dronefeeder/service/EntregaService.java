package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.exceptions.NaoEncontradoException;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    if (entregaRepo.findById(id).isEmpty()) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }
    Entrega entrega = entregaRepo.findById(id).get();
    return entrega;
  }

  /**
   * Retorna Drone que está com a entrega correspondente.
   *
   * @param id ID da Entrega
   * @return Drone Correspondente
   */
  public Drone findDrone(Long id) {
    if (entregaRepo.findById(id).isEmpty()) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }
    Entrega entrega = entregaRepo.findById(id).get();
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
    if (entregaRepo.findById(id).isEmpty()) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }

    Entrega entregaParaAtualizar = entregaRepo.findById(id).get();

    entregaParaAtualizar.setDataHoraEntrega(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

    switch (entrega.getStatusDaEntrega()) {
      case ENTREGUE:
        entregaParaAtualizar.setStatusDaEntrega("ENTREGUE");
        break;
      case CANCELADO:
        entregaParaAtualizar.setStatusDaEntrega("CANCELADO");
        break;
      case PENDENTE:
        entregaParaAtualizar.setStatusDaEntrega("PENDENTE");
        break;
      default:
        entregaParaAtualizar.setStatusDaEntrega("EM_ANDAMENTO");
        break;
    }
    return entregaRepo.save(entregaParaAtualizar);
  }

  /**
   * Deleta a entrega correspondente.
   *
   * @param id ID da entrega
   * @return Entrega deletada
   */
  public String delete(Long id) {
    if (entregaRepo.findById(id).isEmpty()) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    }
    entregaRepo.deleteById(id);
    return "Entrega deletada com sucesso!";
  }
}
