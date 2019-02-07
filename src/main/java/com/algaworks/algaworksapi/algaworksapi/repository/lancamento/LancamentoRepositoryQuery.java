package com.algaworks.algaworksapi.algaworksapi.repository.lancamento;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
