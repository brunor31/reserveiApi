package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Reserva {

    private Integer idReserva;
    private Hotel hotel;
    private Quarto quarto;
    private Cliente cliente;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private TipoReserva tipo;
    private Double valorReserva;
}
