package med.vol.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vol.api.domain.endereco.Endereco;
import med.vol.api.domain.paciente.dto.DadosAtualizarPaciente;
import med.vol.api.domain.paciente.dto.DadosCadastroPaciente;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.ativo = true;
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarPaciente dadosAtualizarPaciente) {
        if (dadosAtualizarPaciente.nome() != null) {
            this.nome = dadosAtualizarPaciente.nome();
        }
        if (dadosAtualizarPaciente.telefone() != null) {
            this.telefone = dadosAtualizarPaciente.telefone();
        }
        if (dadosAtualizarPaciente.endereco() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizarPaciente.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
