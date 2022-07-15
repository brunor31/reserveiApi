package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteDTO extends ClienteCreateDTO{

    @Schema(description = "Id do cliente", example = "4")
    private Integer idCliente;
}
