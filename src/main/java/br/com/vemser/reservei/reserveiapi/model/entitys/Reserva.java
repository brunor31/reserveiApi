package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Reserva {

    private Integer idReserva;
    private Integer idHotel;
    private Integer idQuarto;
    private Integer idCliente;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private TipoReserva tipo;
    private Double valorReserva;
}
