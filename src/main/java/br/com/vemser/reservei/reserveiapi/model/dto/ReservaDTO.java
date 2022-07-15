package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReservaDTO extends ReservaCreateDTO {

    @Schema(description = "Id da reserva", example = "68")
    private Integer idReserva;
    @Schema(description = "Informa o valor total da reserva", example = "1575.00")
    private Double valorReserva;
}
