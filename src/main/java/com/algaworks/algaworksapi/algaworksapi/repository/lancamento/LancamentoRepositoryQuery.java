package com.algaworks.algaworksapi.algaworksapi.repository.lancamento;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.LancamentoFilter;
import com.algaworks.algaworksapi.algaworksapi.repository.projection.Resumolancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    public Page<Resumolancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
