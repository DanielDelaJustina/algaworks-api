package com.algaworks.algaworksapi.algaworksapi.repository.pessoa;

import com.algaworks.algaworksapi.algaworksapi.model.Pessoa;
import com.algaworks.algaworksapi.algaworksapi.model.Pessoa_;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.FiltroGenerico;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.PessoasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Pessoa> filtrar(PessoasFilter pessoasFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        //Adicionar os filtros
        Predicate[] predicates = criarRestricoes(pessoasFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Pessoa> query = manager.createQuery(criteria);

        FiltroGenerico filtroGenerico = new FiltroGenerico();

        filtroGenerico.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(pessoasFilter));
    }

    private Long total( PessoasFilter pessoasFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicates = criarRestricoes(pessoasFilter, builder, root);

        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();

    }

    private Predicate[] criarRestricoes(PessoasFilter pessoasFilter, CriteriaBuilder builder,
                                        Root<Pessoa> root) {

        List<Predicate> predicates = new ArrayList<>();

        // where lower(descricao) like lower('%descrição enviada pelo usuário%')
        if (!StringUtils.isEmpty(pessoasFilter.getNome())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Pessoa_.nome)), "%" + pessoasFilter.getNome().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
