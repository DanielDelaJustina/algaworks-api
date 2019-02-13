package com.algaworks.algaworksapi.algaworksapi.repository.filter;

import org.springframework.data.domain.Pageable;

import javax.persistence.TypedQuery;

public class FiltroGenerico {

    public void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        Integer paginaAtual = pageable.getPageNumber();
        Integer totalDeRegistrosPorPagina = pageable.getPageSize();
        Integer primeiroRegistroDaPagina = paginaAtual * totalDeRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalDeRegistrosPorPagina);

    }

}
