package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.ConsultaRepository;
import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var MedicoPossuiOutraConsultaNoMesmoHorario =
                consultaRepository.existsByMedicoIdAndData(
                        dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());

        if (MedicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada no mesmo horário");
        }
    }
}
