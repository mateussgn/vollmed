package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.paciente.dto.DadosCadastroPaciente;
import med.vol.api.domain.paciente.Paciente;
import med.vol.api.domain.paciente.PacienteRepository;
import med.vol.api.domain.paciente.dto.DadosListagemPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<DadosListagemPaciente> listar() {
        return pacienteRepository.findAll().stream().map(DadosListagemPaciente::new).toList();
    }
}
