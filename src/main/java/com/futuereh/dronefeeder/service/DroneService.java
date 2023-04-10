package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.exceptions.NaoEncontradoException;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.DroneRepository;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import com.futuereh.dronefeeder.repository.StatusDaEntrega;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**Classe de serviço DroneService.*/
@Service
public class DroneService {
  @Autowired
  private DroneRepository repository;
  
  @Autowired
  private EntregaRepository entregaRepo;
  
  /**Método de inserir novo drone.*/
  public Drone inserirDrone(Drone drone) {
    return repository.save(drone);
  }
  
  /**Método de retornar todos os drones.*/
  public List<Drone> retornarTodosOsDrones() {
    return repository.findAll();
  }

  /**Método de retornar drone pelo id.*/
  public Drone retornarDronePeloId(Long id) {
    Drone drone = repository.findById(id).get();

    if (!repository.existsById(drone.getId())) {
      throw new NaoEncontradoException("Drone não encontrado.");
    } else {
      return drone;
    }
  }
  
  /**Método de atualizar localização do drone.*/
  public Drone atualizarLocalizacaoDrone(Long id, double latitude, double longitude) {
    Drone drone = repository.findById(id).get();

    if (!repository.existsById(drone.getId())) {
      throw new NaoEncontradoException("Drone não encontrado.");
    } else {
      drone.setLatitudeAtual(latitude);
      drone.setLongitudeAtual(longitude);
      return repository.save(drone);
    }
  }
  
  /**Método de deletar drone pelo id.*/
  public String deletarDrone(Long id) {
    Drone drone = repository.findById(id).get();

    if (!repository.existsById(drone.getId())) {
      throw new NaoEncontradoException("Drone não encontrado.");
    } else {
      repository.deleteById(id);
      return "Drone deletado com sucesso!";
    }
  }
  
  /**Método de adicionar entregas ao drone.*/
  public Drone adicionarEntrega(Long droneId, Long entregaId) {
    Drone drone = repository.findById(droneId).get();
    Entrega entrega = entregaRepo.findById(entregaId).get();
    
    if (!repository.existsById(drone.getId())) {
      throw new NaoEncontradoException("Drone não encontrado.");
      
    } else if (!entregaRepo.existsById(entrega.getId())) {
      throw new NaoEncontradoException("Entrega não encontrada.");
    } else {
      entrega.setDrone(drone);
      entrega.setStatusDaEntrega(StatusDaEntrega.EM_ANDAMENTO);
      entrega.setDataHoraRetirada(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
      drone.adicionarEntrega(entrega);
      return repository.save(drone);
    }
  }
  
  /**Método de retornar todas as entregas de um determinado drone.*/
  public List<Entrega> retornarEntregasDoDrone(Long id) {
    Drone drone = repository.findById(id).get();
    
    if (!repository.existsById(drone.getId())) {
      throw new NaoEncontradoException("Drone não encontrado.");
    } else {
      return drone.getEntregas();
    }
  }
}
