package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HotelDTO extends HotelCreateDTO{

    @Schema(description = "Id do hotel", example = "5")
    private Integer idHotel;
}
