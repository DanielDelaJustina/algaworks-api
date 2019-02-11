package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.event.RecursoCriadoEvent;
import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.PessoaRepository;
import com.algaworks.algaworksapi.algaworksapi.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Pessoa>> listarPessoas() {

        List<Pessoa> pessoas = pessoaRepository.findAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaRepository.findOne(id);
        return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

        Pessoa pessoaSave = pessoaRepository.save(pessoa);

        //Montar URI para buscar o registro gerado atualmente, retornando ao ser inserido.
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSave.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSave);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    public void excluirPessoa(@PathVariable Long id) {
        pessoaRepository.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {

        Pessoa pessoaSalva = pessoaService.atualizarPessoa(id, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public void atualizarAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {

        pessoaService.atualizarAtivo(id, ativo );
    }
}
