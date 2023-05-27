package com.sd.csgobrasil.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skin{
    
    private Long id;
    private String nome;
    private String arma;
    private int preco;
    private String raridade;

    private String imagem;

    public Skin() {
    }

}
