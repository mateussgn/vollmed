package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.medico.dto.DadosAtualizarMedico;
import med.vol.api.domain.medico.dto.DadosCadastroMedico;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.medico.dto.DadosListagemMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        medicoRepository.save(new Medico(dadosCadastroMedico));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        return medicoRepository.findAll(pageable).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarMedico dadosAtualizarMedico) {
        var medico = medicoRepository.getReferenceById(dadosAtualizarMedico.id());
        medico.atualizarInformacoes(dadosAtualizarMedico);
    }
}
