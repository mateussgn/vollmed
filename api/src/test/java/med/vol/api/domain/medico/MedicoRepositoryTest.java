package med.vol.api.domain.medico;

import med.vol.api.domain.consulta.Consulta;
import med.vol.api.domain.endereco.dto.DadosEndereco;
import med.vol.api.domain.medico.dto.DadosCadastroMedico;
import med.vol.api.domain.paciente.Paciente;
import med.vol.api.domain.paciente.dto.DadosCadastroPaciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        //given or arrange
        var proximaSegundaAs10 = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = cadastrarMedico("Tiago Santana", "tiago@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Mateus Santana", "mateus@voll.med", "05111158308");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when or act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then or assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        //given or arrange
        var proximaSegundaAs10 = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = cadastrarMedico("Tiago Santana", "tiago@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when or act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then or assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        var consulta = new Consulta(null, medico, paciente, data, null);
        testEntityManager.persist(consulta);
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        testEntityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "81999562343",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "81999562343",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua Caio Pereira",
                "Rosarinho",
                "52041045",
                "Recife",
                "PE",
                "AP 1803",
                "225"
        );
    }

}