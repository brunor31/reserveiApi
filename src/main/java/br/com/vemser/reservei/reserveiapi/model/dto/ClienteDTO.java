package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClienteDTO {

    @Schema(description = "Nome do cliente", example = "Pedro Augusto")
    private String nome;
    @Schema(description = "Cpf do cliente, (precisa ser válido)", example = "63510692039")
    private String cpf;
    @Schema(description = "Número de telefone do cliente", example = "51996031653")
    private String telefone;
    @Schema(description = "E-mail do cliente", example = "brunoroliveira_@outlook.com")
    private String email;
    @Schema(description = "Id do cliente", example = "4")
    private Integer idCliente;
}
