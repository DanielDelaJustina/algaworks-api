package com.algaworks.algaworksapi.algaworksapi.repository.lancamento;

import com.algaworks.algaworksapi.algaworksapi.model.Categoria_;
import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.model.Lancamento_;
import com.algaworks.algaworksapi.algaworksapi.model.Pessoa_;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.FiltroGenerico;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.LancamentoFilter;
import com.algaworks.algaworksapi.algaworksapi.repository.projection.Resumolancamento;
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

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        //Adicionar os filtros
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);

        FiltroGenerico filtroGenerico = new FiltroGenerico();

        filtroGenerico.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    @Override
    public Page<Resumolancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Resumolancamento> criteria = builder.createQuery(Resumolancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(Resumolancamento.class
                                , root.get(Lancamento_.id), root.get(Lancamento_.descricao)
                                , root.get(Lancamento_.datavencimento), root.get(Lancamento_.datapagamento)
                                , root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
                                , root.get(Lancamento_.categoria).get(Categoria_.nome)
                                , root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

        //Adicionar os filtros
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Resumolancamento> query = manager.createQuery(criteria);

        FiltroGenerico filtroGenerico = new FiltroGenerico();

        filtroGenerico.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    private Long total( LancamentoFilter lancamentoFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();

    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
                                        Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        // where lower(descricao) like lower('%descrição enviada pelo usuário%')
        if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Lancamento_.datavencimento), lancamentoFilter.getDataVencimentoDe())
            );
        }

        if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Lancamento_.datavencimento), lancamentoFilter.getDataVencimentoAte())
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
