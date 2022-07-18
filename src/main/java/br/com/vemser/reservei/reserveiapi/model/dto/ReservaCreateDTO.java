package br.com.vemser.reservei.reserveiapi.model.dto;

import br.com.vemser.reservei.reserveiapi.model.entitys.TipoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReservaCreateDTO {

    @NotNull
    @Schema(description = "Id do hotel", example = "5")
    private Integer idHotel;
    @NotNull
    @Schema(description = "Número do quarto no hotel", example = "201")
    private Integer idQuarto;
    @NotNull
    @Schema(description = "Informar o cpf do cliente", example = "83988352063")
    private Integer idCliente;
    @Future
    @NotNull
    @Schema(description = "Informar a data de entrada da reserva, deve estar no futuro dd/MM/yyyy", example = "25/08/2022")
    private LocalDate dataEntrada;
    @Future
    @NotNull
    @Schema(description = "Informar a data de saída da reserva, deve estar no futuro dd/MM/yyyy", example = "30/08/2022")
    private LocalDate dataSaida;
    @NotNull
    @Schema(description = "Informar o tipo da reserva, [1 - Comum, 2 - Premium", example = "RESERVA_COMUM")
    private TipoReserva tipo;
}
