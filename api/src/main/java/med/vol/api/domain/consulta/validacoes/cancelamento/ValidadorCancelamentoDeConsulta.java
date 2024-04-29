package med.vol.api.domain.consulta.validacoes.cancelamento;

import med.vol.api.domain.consulta.dto.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    public void validar(DadosCancelamentoConsulta dadosCancelamentoConsulta);
}
