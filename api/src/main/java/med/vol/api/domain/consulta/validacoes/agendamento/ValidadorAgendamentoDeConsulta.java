package med.vol.api.domain.consulta.validacoes.agendamento;

import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta);
}
