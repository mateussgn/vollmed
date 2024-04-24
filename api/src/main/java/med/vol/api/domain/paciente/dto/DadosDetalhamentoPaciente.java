package med.vol.api.domain.paciente.dto;

import med.vol.api.domain.endereco.Endereco;
import med.vol.api.domain.paciente.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone,
                                        String cpf, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(),
                paciente.getCpf(), paciente.getEndereco());
    }
}
