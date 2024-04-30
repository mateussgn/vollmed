package med.vol.api.domain.consulta.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import med.vol.api.domain.consulta.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @Id
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
