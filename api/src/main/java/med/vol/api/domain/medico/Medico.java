package med.vol.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vol.api.domain.endereco.Endereco;
import med.vol.api.domain.medico.dto.DadosAtualizarMedico;
import med.vol.api.domain.medico.dto.DadosCadastroMedico;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.ativo = true;
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarMedico dadosAtualizarMedico) {
        if (dadosAtualizarMedico.nome() != null) {
            this.nome = dadosAtualizarMedico.nome();
        }
        if (dadosAtualizarMedico.telefone() != null) {
            this.telefone = dadosAtualizarMedico.telefone();
        }
        if (dadosAtualizarMedico.endereco() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizarMedico.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
