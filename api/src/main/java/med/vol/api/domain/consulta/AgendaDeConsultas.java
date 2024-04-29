package med.vol.api.domain.consulta;

import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.vol.api.domain.consulta.dto.DadosCancelamentoConsulta;
import med.vol.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    List<ValidadorAgendamentoDeConsulta> validadores;

    public void agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {

        if (!pacienteRepository.existsById(dadosAgendamentoConsulta.idMedico())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dadosAgendamentoConsulta.idMedico() != null && !medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(validador -> validador.validar(dadosAgendamentoConsulta));

        var medico = escolherMedico(dadosAgendamentoConsulta);
        var paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data(), null);
        consultaRepository.save(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dadosCancelamentoConsulta) {
        if (consultaRepository.existsById(dadosCancelamentoConsulta.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe");
        }

        var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idConsulta());
        consulta.cancelar(dadosCancelamentoConsulta.motivo());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idMedico() != null) {
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }

        if (dadosAgendamentoConsulta.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(
                dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data());
    }

}
