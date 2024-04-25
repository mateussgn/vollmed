package med.vol.api.domain.medico.dto;

import med.vol.api.domain.endereco.Endereco;
import med.vol.api.domain.medico.Especialidade;
import med.vol.api.domain.medico.Medico;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone,
                                      Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(),
                medico.getEspecialidade(), medico.getEndereco());
    }
}
