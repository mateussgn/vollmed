package med.vol.api.controller;

import med.vol.api.domain.endereco.Endereco;
import med.vol.api.domain.endereco.dto.DadosEndereco;
import med.vol.api.domain.medico.Especialidade;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.medico.dto.DadosCadastroMedico;
import med.vol.api.domain.medico.dto.DadosDetalhamentoMedico;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJacksonTester;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        //given or arrange
        //when or act
        var response = mockMvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        //then or assert
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {

        //given or arrange
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                dadosEndereco());
        var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastroMedico.nome(),
                dadosCadastroMedico.email(),
                dadosCadastroMedico.crm(),
                dadosCadastroMedico.telefone(),
                dadosCadastroMedico.especialidade(),
                new Endereco(dadosCadastroMedico.endereco())
        );
        var jsonEsperado = dadosDetalhamentoMedicoJacksonTester.write(dadosDetalhamento).getJson();

        when(repository.save(any())).thenReturn(new Medico(dadosCadastroMedico));

        //when or act
        var response = mockMvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJacksonTester.write(dadosCadastroMedico).getJson()))
                .andReturn().getResponse();

        //then or assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}