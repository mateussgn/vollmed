package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.medico.dto.DadosDetalhamentoMedico;
import med.vol.api.domain.paciente.dto.DadosAtualizarPaciente;
import med.vol.api.domain.paciente.dto.DadosCadastroPaciente;
import med.vol.api.domain.paciente.Paciente;
import med.vol.api.domain.paciente.PacienteRepository;
import med.vol.api.domain.paciente.dto.DadosDetalhamentoPaciente;
import med.vol.api.domain.paciente.dto.DadosListagemPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dadosCadastroPaciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        pacienteRepository.save(paciente);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        var page = pacienteRepository.findByAtivoTrue(pageable).map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atulizar(@RequestBody @Valid DadosAtualizarPaciente dadosAtualizarPaciente) {
        var paciente = pacienteRepository.getReferenceById(dadosAtualizarPaciente.id());
        paciente.atualizarInformacoes(dadosAtualizarPaciente);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }
}
