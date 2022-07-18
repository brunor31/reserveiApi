package br.com.vemser.reservei.reserveiapi.model.dto;

import br.com.vemser.reservei.reserveiapi.model.entitys.TipoQuarto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuartoCreateDTO {

    @NotNull
    @Schema(description = "Id do hotel que o quarto pertence", example = "idHotel: 1")
    private Integer idHotel;
    @NotNull
    @Schema(description = "Número do quarto", example = "501")
    private Integer numero;
    @NotNull
    @Schema(description = "Tipo do quarto [1 - Solteiro, 2 - Casal]", example = "QUARTO_SOLTEIRO")
    private TipoQuarto tipoQuarto;
    @NotNull
    @Schema(description = "Valor da diária do quarto", example = "250.00")
    private Double precoDiaria;
}
