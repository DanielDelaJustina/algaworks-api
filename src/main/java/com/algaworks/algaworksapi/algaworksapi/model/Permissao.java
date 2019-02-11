package com.algaworks.algaworksapi.algaworksapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissao")
@Getter
@Setter
@EqualsAndHashCode
public class Permissao {

    @Id
    private Long id;

    private String descricao;
}
