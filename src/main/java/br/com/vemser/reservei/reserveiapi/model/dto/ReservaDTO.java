package br.com.vemser.reservei.reserveiapi.model.dto;

import lombok.Data;

@Data
public class ReservaDTO extends ReservaCreateDTO{

    private Double valorReserva;
    private Integer idReserva;
}
