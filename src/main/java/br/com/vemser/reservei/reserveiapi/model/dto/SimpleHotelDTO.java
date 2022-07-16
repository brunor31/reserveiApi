package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SimpleHotelDTO {

    @Schema(description = "Nome do hotel", example = "Ibis")
    private String nome;
    @Schema(description = "Nome da cidade", example = "Porto Alegre")
    private String cidade;
    @Schema(description = "Id do hotel", example = "5")
    private Integer idHotel;
}
