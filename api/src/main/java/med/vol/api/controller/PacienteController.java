package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.paciente.dto.DadosAtualizarPaciente;
import med.vol.api.domain.paciente.dto.DadosCadastroPaciente;
import med.vol.api.domain.paciente.Paciente;
import med.vol.api.domain.paciente.PacienteRepository;
import med.vol.api.domain.paciente.dto.DadosListagemPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente) {
        pacienteRepository.save(new Paciente(dadosCadastroPaciente));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        return pacienteRepository.findAll(pageable).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atulizar(@RequestBody @Valid DadosAtualizarPaciente dadosAtualizarPaciente) {
        var paciente = pacienteRepository.getReferenceById(dadosAtualizarPaciente.id());
        paciente.atualizarInformacoes(dadosAtualizarPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        pacienteRepository.deleteById(id);
    }
}
