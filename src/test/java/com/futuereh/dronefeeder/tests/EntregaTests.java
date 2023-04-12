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
import com.futuereh.dronefeeder.repository.EntregaRepository;
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
class EntregaTests {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private EntregaRepository entregaRepository;
  
  @SpyBean
  private DroneRepository droneRepository;

  @Captor
  private ArgumentCaptor<Entrega> entregaCaptor;
  
  @BeforeEach
  public void setup() {
    entregaRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("1 - Deve adicionar uma entrega na base de dados.")
  void deveAdicionarEntregaNaBaseDeDados() throws Exception {
    Entrega entrega = new Entrega(-15.0965, -45.6821);
    
    mockMvc
        .perform(post("/entrega")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(entrega)))
        
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDestino").value(entrega.getLatitudeDestino()));

    verify(entregaRepository, atLeast(1)).save(entregaCaptor.capture());

    assertThat(entregaCaptor.getValue()).isNotNull();
    assertThat(entregaCaptor.getValue().getId()).isNotNull();
    assertThat(entregaCaptor.getValue().getLatitudeDestino()).isEqualTo(-15.0965);
    assertThat(entregaCaptor.getValue().getLongitudeDestino()).isEqualTo(-45.6821);
  }
  
  @Test
  @Order(2)
  @DisplayName("2 - Deve atualizar entrega existente, pelo Id, da base de dados")
  void deveAtualizarEntregaExistentePeloIdNaBase() throws Exception {
    Entrega entrega = new Entrega(-15.0965, -45.6821);

    entrega.setDataHoraEntrega(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    
    entrega.setStatusDaEntrega("ENTREGUE");
    
    entregaRepository.save(entrega);
    
    mockMvc.perform(put("/entrega/" + entrega.getId()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(entrega)))

        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusDaEntrega").value("ENTREGUE"));
    
    entrega.setStatusDaEntrega("PENDENTE");
    
    mockMvc.perform(put("/entrega/" + entrega.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(entrega)))

            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusDaEntrega").value("PENDENTE"));
    
    
    entrega.setStatusDaEntrega("EM_ANDAMENTO");
    
    mockMvc.perform(put("/entrega/" + entrega.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(entrega)))

            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusDaEntrega").value("EM_ANDAMENTO"));
    
    entrega.setStatusDaEntrega("CANCELADO");
    
    mockMvc.perform(put("/entrega/" + entrega.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(entrega)))

            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusDaEntrega").value("CANCELADO"));
  }
  
  @Test
  @Order(3)
  @DisplayName("3 - Deve retornar todas as entregas existentes da base de dados.")
  void deveRetornarTodasEntregasExistentesNaBase() throws Exception {
    Entrega entrega1 = new Entrega(-15.0965, -45.6821);
    Entrega entrega2 = new Entrega(-45.9332, -45.9332);
    
    entregaRepository.save(entrega1);
    entregaRepository.save(entrega2);

    mockMvc.perform(get("/entrega").contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].latitudeDestino").value(-15.0965))
        .andExpect(jsonPath("$[1].latitudeDestino").value(-45.9332));
  }
  
  @Test
  @Order(4)
  @DisplayName("4 - Deve retornar drone responsavel pela entrega existente base de dados.")
  void deveRetornarDroneResponsavelPelaEntregasExistenteNaBase() throws Exception {
    Entrega entrega = new Entrega(-45.9332, -45.9332);
    Drone drone = new Drone("X44", -15.0965, -45.6821);

    entrega.setDrone(drone);
    entrega.setStatusDaEntrega("EM_ANDAMENTO");
    entrega.setDataHoraRetirada(LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    drone.adicionarEntrega(entrega);
    
    droneRepository.save(drone);
    
    mockMvc.perform(get("/entrega/" + entrega.getId() + "/drone")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.modelo").value(drone.getModelo()));
  }
  
  @Test
  @Order(5)
  @DisplayName("5 - Deve retornar entrega existente, pelo Id, da base de dados")
  void deveRetornarEntregaExistentePeloIdNaBase() throws Exception {
    Entrega entrega = new Entrega(-45.9332, -45.9332);
    entregaRepository.save(entrega);
        
    mockMvc.perform(get("/entrega/" + entrega.getId())
        .contentType(MediaType.APPLICATION_JSON))

        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.latitudeDestino").value(-45.9332));
  }
  
  @Test
  @Order(6)
  @DisplayName("6 - Deve emitir 'não encontrado' quando tenta acessar uma entrega não existente.")
  void deveEmitirNaoEncontradoQuandoEntregaNaoExistirNaBase() throws Exception {
    mockMvc.perform(get("/entrega/" + new Random().nextInt()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Entrega não encontrada."));
  }
  
  @Test
  @Order(7)
  @DisplayName("7 - Deve remover entrega, por um id existente informado.")
  void deveRemoverEntregaQuandoExistirNaBase() throws Exception {
    Entrega entrega = entregaRepository.save(new Entrega(-45.9332, -45.9332));

    mockMvc.perform(delete("/entrega/" + entrega.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Entrega deletada com sucesso!")));
  }
}
  