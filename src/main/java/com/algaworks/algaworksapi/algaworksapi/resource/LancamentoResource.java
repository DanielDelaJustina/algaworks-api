package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.event.RecursoCriadoEvent;
import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/lancamentos")
@RestController
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<?> buscarLancamentos() {

        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        return ResponseEntity.ok().body(lancamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarLancamento(@PathVariable Long id) {

        Lancamento lancamento = lancamentoRepository.findOne(id);
        return lancamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lancamento);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criarLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {

        Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }
}
