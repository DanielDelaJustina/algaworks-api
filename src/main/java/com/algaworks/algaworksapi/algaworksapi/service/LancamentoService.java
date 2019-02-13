package com.algaworks.algaworksapi.algaworksapi.service;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.LancamentoRepository;
import com.algaworks.algaworksapi.algaworksapi.repository.PessoaRepository;
import com.algaworks.algaworksapi.algaworksapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Lancamento atualizarLancamento(Long id, Lancamento lancamento) {

        Lancamento lancamentoSalvo = getLancamento(id);

        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = null;

        if (lancamento.getPessoa().getId() != null ) {
            pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
        }

        if (pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento getLancamento(Long id) {
        Lancamento lancamento = lancamentoRepository.findOne(id);

        if (lancamento == null) {
            throw new IllegalArgumentException();
        }

        return lancamento;
    }
}
