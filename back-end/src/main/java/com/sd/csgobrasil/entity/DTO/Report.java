package com.sd.csgobrasil.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private Long idVenda;
    private String nameVendedor;
    private String nameComprador;
    private String nameSkin;
    private boolean estadoVenda;
    private int pontos;
}
