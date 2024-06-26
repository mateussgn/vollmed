package med.vol.api.domain.paciente.dto;

import med.vol.api.domain.paciente.Paciente;

public record DadosListagemPaciente (Long id, String nome, String email, String telefone, String cpf){

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
