package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

@Data
public class Quarto {

    private Integer idQuarto;
    private Integer idHotel;
    private Integer numero;
    private TipoQuarto tipoQuarto;
    private Double precoDiaria;
}
