package com.algaworks.algaworksapi.algaworksapi.repository.pessoa;

import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.PessoasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

    public Page<Pessoa> filtrar(PessoasFilter pessoasFilter, Pageable pageable);
}
