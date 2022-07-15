package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuartoDTO extends QuartoCreateDTO{

    @Schema(description = "Id do quarto", example = "Id: 3")
    private Integer idQuarto;
}
