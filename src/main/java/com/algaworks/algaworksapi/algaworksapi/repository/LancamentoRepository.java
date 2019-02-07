package com.algaworks.algaworksapi.algaworksapi.repository;

import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
