package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

@Data
public class Quarto {

    private Integer idQuarto;
    private Hotel hotel;
    private Integer numero;
    private TipoQuarto tipo;
    private Double precoDiaria;
}
