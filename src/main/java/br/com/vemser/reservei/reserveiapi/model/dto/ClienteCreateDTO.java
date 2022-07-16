package br.com.vemser.reservei.reserveiapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClienteCreateDTO {

    @NotEmpty
    @Schema(description = "Nome do cliente", example = "Pedro Augusto")
    private String nome;
    @NotEmpty
    @CPF
    @Schema(description = "Cpf do cliente, (precisa ser válido)", example = "63510692039")
    private String cpf;
    @NotEmpty
    @Size(min = 10, max = 14)
    @Schema(description = "Número de telefone do cliente", example = "51996031653")
    private String telefone;
    @NotEmpty
    @Schema(description = "E-mail do cliente", example = "brunoroliveira_@outlook.com")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[@!#$%^&*()/\\\\])[@!#$%^&*()/\\\\a-zA-Z0-9]{8,20}")
    @Schema(description = "Senha do cliente, deve contar no mínimo 8 digitos, letras, número e caracter especial", example = "12asdde*")
    private String senha;
}
