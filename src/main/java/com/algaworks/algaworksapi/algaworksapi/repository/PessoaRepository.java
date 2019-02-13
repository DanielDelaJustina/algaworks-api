package com.algaworks.algaworksapi.algaworksapi.repository;

import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.repository.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
