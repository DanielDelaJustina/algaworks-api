package com.algaworks.algaworksapi.algaworksapi.model;

import com.algaworks.algaworksapi.algaworksapi.enumeration.Tipolancamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
@EqualsAndHashCode
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;

    @Column(name = "data_vencimento")
    private LocalDate datavencimento;

    @Column(name = "data_pagamento")
    private LocalDate datapagamento;

    private BigDecimal valor;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private Tipolancamento tipo;

    //uma categoria pode estar em varios lançamentos
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    //uma pessoa pode estar em varios lançamentos
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

}
