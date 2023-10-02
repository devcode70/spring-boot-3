package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    //@Secured("ROLE_ADMIN")
    public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dadosMedico, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dadosMedico);
        repository.save(medico);
        var uri = uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping                                                    //@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 10)
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(@PageableDefault(page = 0, size = 10) Pageable paginacao){
        var pageResponse = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalharMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dadosMedico){

        var medico = repository.getReferenceById(dadosMedico.id());
        medico.atualizarInformacao(dadosMedico);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirMedico(@PathVariable Long id){

        var medico = repository.getReferenceById(id);
        medico.desativar();
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/ativar/{id}")
    @Transactional
    public ResponseEntity ativarMedico(@PathVariable Long id){

        var medico = repository.getReferenceById(id);
        medico.ativar();
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }
}
