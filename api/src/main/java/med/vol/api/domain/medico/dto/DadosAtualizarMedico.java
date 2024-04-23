package med.vol.api.domain.medico.dto;

import med.vol.api.domain.endereco.dto.DadosEndereco;

public record DadosAtualizarMedico(Long id, String nome, String telefone, DadosEndereco endereco) {

}
