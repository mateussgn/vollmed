package med.vol.api.controller;

import med.vol.api.domain.consulta.AgendaDeConsultas;
import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.vol.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.vol.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @Autowired
    JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver código 400 quando informações estão inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        //given or arrange
        //when or act
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        //then or assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código 200 quando informações estão válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        //given or arrange
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosAgendamentoConsulta = new DadosAgendamentoConsulta(2L, 5L, data, especialidade);
        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2L, 5L, data);
        var jsonEsperado = dadosDetalhamentoConsultaJacksonTester.write(dadosDetalhamentoConsulta).getJson();

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentoConsulta);

        //when or act
        var response = mockMvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJacksonTester.write(dadosAgendamentoConsulta)
                                        .getJson()))
                .andReturn()
                .getResponse();

        //then or assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}