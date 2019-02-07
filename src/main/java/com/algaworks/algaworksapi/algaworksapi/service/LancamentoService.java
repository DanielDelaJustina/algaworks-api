package com.algaworks.algaworksapi.algaworksapi.service;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.LancamentoRepository;
import com.algaworks.algaworksapi.algaworksapi.repository.PessoaRepository;
import com.algaworks.algaworksapi.algaworksapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {

        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
        if (pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }
}
