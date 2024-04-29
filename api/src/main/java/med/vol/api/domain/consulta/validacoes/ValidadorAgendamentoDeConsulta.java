package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta);
}
