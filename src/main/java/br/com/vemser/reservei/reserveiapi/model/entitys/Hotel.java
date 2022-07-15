package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

import java.util.List;
@Data
public class Hotel {

    private Integer idHotel;
    private String nome;
    private String cidade;
    private String telefone;
    private Integer classificacao;
    private List<Quarto> quartos;
}
