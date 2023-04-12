package com.futuereh.dronefeeder.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.DroneRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DroneTests {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private DroneRepository droneRepository;
  
  @Captor
  private ArgumentCaptor<Drone> droneCaptor;
  
  @BeforeEach
  public void setup() {
    droneRepository.deleteAll();
  }
  
  @Test
  @Order(1)
  @DisplayName("1 -  Deve adicionar um drone na base de dados.")
  void deveAdicionarDroneNaBaseDeDados() throws Exception {
    Drone drone = new Drone("X44", -15.0965, -45.6821);
    
    mockMvc
        .perform(post("/drone")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(drone)))
        
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.modelo").value(drone.getModelo()));

    verify(droneRepository, atLeast(1)).save(droneCaptor.capture());

    assertThat(droneCaptor.getValue()).isNotNull();
    assertThat(droneCaptor.getValue().getId()).isNotNull();
    assertThat(droneCaptor.getValue().getModelo()).isEqualTo(drone.getModelo());
    assertThat(droneCaptor.getValue().getEntregas()).isEmpty();
  }
  
  @Test
  @Order(2)
  @DisplayName("2 - Deve retornar todos os drones existentes da base de dados.")
  void deveRetornarTodosDronesExistentesNaBase() throws Exception {
    Drone drone1 = new Drone("X44", -15.0965, -45.6821);
    Drone drone2 = new Drone("X56", -45.9332, -45.9332);
    droneRepository.save(drone1);
    droneRepository.save(drone2);

    mockMvc.perform(get("/drone").contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].modelo").value(drone1.getModelo()))
        .andExpect(jsonPath("$[1].modelo").value(drone2.getModelo()));
  }

  @Test
  @Order(3)
  @DisplayName("3 - Deve retornar lista vazia quando não existir drones na base de dados.")
  void deveRetornarListaVaziaQuandoNaoExistirDronesNaBase() throws Exception {
    mockMvc.perform(get("/drone").contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }
  
  @Test
  @Order(4)
  @DisplayName("4 - Deve remover drone, por um id existente informado.")
  void deveRemoverDroneQuandoExistirNaBase() throws Exception {
    Drone drone = droneRepository.save(new Drone("X44", -15.0965, -45.6821));;

    mockMvc.perform(delete("/drone/" + drone.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Drone deletado com sucesso!")));
  }
  
  @Test
  @Order(5)
  @DisplayName("5 - Deve emitir 'não encontrado' quando tenta acessar um drone não existente.")
  void deveEmitirNaoEncontradoQuandoDroneNaoExistirNaBase() throws Exception {
    mockMvc.perform(get("/drone/" + new Random().nextInt()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Drone não encontrado."));
  }
  
  @Test
  @Order(6)
  @DisplayName("6 - Deve retornar drone existente, pelo Id, da base de dados")
  void deveRetornarDroneExistentePeloIdNaBase() throws Exception {
    Drone drone = new Drone("X44", -15.0965, -45.6821);
    droneRepository.save(drone);
        
    mockMvc.perform(get("/drone/" + drone.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.modelo").value(drone.getModelo()));
  }
  
  @Test
  @Order(7)
  @DisplayName("7 - Deve adicionar entrega quando existir Drone na base de dados.")
  void deveAdicionarEntregaQuandoExistirDroneNaBaseDeDados() throws Exception {
    Drone drone = new Drone("X44", -15.0965, -45.6821);
    Entrega entrega = new Entrega(-45.9332, -45.9332);
  
    entrega.setDrone(drone);
    entrega.setStatusDaEntrega("EM_ANDAMENTO");
    entrega.setDataHoraRetirada(LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    drone.adicionarEntrega(entrega);
    
    droneRepository.save(drone);
    
    Long droneId = drone.getId();
    Long entregaId = entrega.getId();
    
    String path = String.format("/drone/%d/entrega/%d", droneId, entregaId);
    mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(drone)))

        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.entregas[0].id").value(entregaId));
    
    verify(droneRepository, atLeast(1)).save(droneCaptor.capture());

    assertThat(droneCaptor.getValue()).isNotNull();
    assertThat(droneCaptor.getValue().getId()).isNotNull();
    assertThat(droneCaptor.getValue().getModelo()).isEqualTo(drone.getModelo());
    assertThat(droneCaptor.getValue().getEntregas()).hasSize(1);
  }
  
  @Test
  @Order(8)
  @DisplayName("8 - Deve emitir 'não encontrado' quando tenta acessar uma entrega não existente.")
  void deveEmitirEntregaNaoEncontradaQuandoDroneNaoExistirNaBase() throws Exception {
    Drone drone = droneRepository.save(new Drone("X44", -15.0965, -45.6821));
    
    mockMvc.perform(post("/drone/" + drone.getId() + "/entrega/" + new Random().nextInt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(drone)))

        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Entrega não encontrada."));
  }
  
  @Test
  @Order(9)
  @DisplayName("9 - Deve atualizar drone existente, pelo Id, da base de dados")
  void deveAtualizarDroneExistentePeloIdNaBase() throws Exception {
    Drone drone = droneRepository.save(new Drone("X44", -15.0965, -45.6821));

    drone.setLatitudeAtual(-45.9332);
    drone.setLongitudeAtual(-45.9332);
    
    droneRepository.save(drone);
    
    mockMvc.perform(put("/drone/" + drone.getId()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(drone)))

        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.latitudeAtual").value(-45.9332));
  }
  
  @Test
  @Order(10)
  @DisplayName("10 - Deve retornar todas as entregas existentes, do Drone, da base de dados.")
  void deveRetornarTodasEntregasExistentesDoDroneNaBase() throws Exception {
    Drone drone = new Drone("X44", -15.0965, -45.6821);
    Entrega entrega = new Entrega(-45.9332, -45.9332);
    
    entrega.setDrone(drone);
    entrega.setStatusDaEntrega("EM_ANDAMENTO");
    entrega.setDataHoraRetirada(LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    drone.adicionarEntrega(entrega);
    
    droneRepository.save(drone);

    mockMvc.perform(get("/drone/" + drone.getId() + "/entregas")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(entrega.getId()));
    
    verify(droneRepository, atLeast(1)).save(droneCaptor.capture());

    assertThat(droneCaptor.getValue()).isNotNull();
    assertThat(droneCaptor.getValue().getId()).isNotNull();
    assertThat(droneCaptor.getValue().getModelo()).isEqualTo(drone.getModelo());
    assertThat(droneCaptor.getValue().getEntregas()).hasSize(1);
  }
  
}
