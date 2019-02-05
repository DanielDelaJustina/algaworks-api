package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {

        List<Pessoa> pessoas = pessoaRepository.findAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaRepository.findOne(id);
        return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

        Pessoa pessoaSave = pessoaRepository.save(pessoa);

        //Montar URI para buscar o registro gerado atualmente, retornando ao ser inserido.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pessoaSave.getId()).toUri();
        response.setHeader("Location",uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaSave);
    }
}
