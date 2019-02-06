package com.algaworks.algaworksapi.algaworksapi.model;

import com.algaworks.algaworksapi.algaworksapi.enumeration.Tipolancamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String descricao;

    @NotNull
    @Column(name = "data_vencimento")
    private LocalDate datavencimento;

    @Column(name = "data_pagamento")
    private LocalDate datapagamento;

    @NotNull
    private BigDecimal valor;

    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipolancamento tipo;

    //uma categoria pode estar em varios lançamentos
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    //uma pessoa pode estar em varios lançamentos
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

}
