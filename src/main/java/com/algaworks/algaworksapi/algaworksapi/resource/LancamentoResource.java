package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/lancamentos")
@RestController
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

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
}
