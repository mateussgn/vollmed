package med.vol.api.domain.consulta.validacoes.agendamento;

import med.vol.api.domain.consulta.ConsultaRepository;
import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var MedicoPossuiOutraConsultaNoMesmoHorario =
                consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(
                        dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());

        if (MedicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada no mesmo horário");
        }
    }
}
