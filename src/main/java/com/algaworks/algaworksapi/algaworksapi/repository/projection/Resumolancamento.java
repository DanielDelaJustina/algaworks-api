package com.algaworks.algaworksapi.algaworksapi.repository.projection;

import com.algaworks.algaworksapi.algaworksapi.enumeration.Tipolancamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Resumolancamento {

    private Long id;
    private String descricao;
    private LocalDate datavencimento;
    private LocalDate datapagamento;
    private BigDecimal valor;
    private Tipolancamento tipo;
    private String categoria;
    private String pessoa;
}
