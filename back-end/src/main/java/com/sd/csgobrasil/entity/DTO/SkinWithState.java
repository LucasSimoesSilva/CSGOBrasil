package com.sd.csgobrasil.entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkinWithState {

    private boolean isInMovement;
    private Long idVenda;
    private Long idSkin;
    private String nome;
    private String arma;
    private int preco;
    private String raridade;
    private String imagem;
}
