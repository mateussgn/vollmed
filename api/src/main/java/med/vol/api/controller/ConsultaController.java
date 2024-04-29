package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.vol.api.domain.consulta.AgendaDeConsultas;
import med.vol.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.vol.api.domain.consulta.dto.DadosCancelamentoConsulta;
import med.vol.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var dadosDetalhamentoConsulta = agendaDeConsultas.agendar(dadosAgendamentoConsulta);

        return ResponseEntity.ok(dadosDetalhamentoConsulta);
    }

    @DeleteMapping
    @Transactional ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta) {
        agendaDeConsultas.cancelar(dadosCancelamentoConsulta);

        return ResponseEntity.noContent().build();
    }
}
