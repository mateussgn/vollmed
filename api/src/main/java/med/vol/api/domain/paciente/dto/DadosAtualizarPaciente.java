package med.vol.api.domain.paciente.dto;

import med.vol.api.domain.endereco.dto.DadosEndereco;

public record DadosAtualizarPaciente(Long id, String nome, String telefone, DadosEndereco endereco) {
}
