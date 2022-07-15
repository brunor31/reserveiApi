package br.com.vemser.reservei.reserveiapi.model.dto;

import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
public class HotelCreateDTO {

    @NotEmpty
    @Schema(description = "Nome do hotel", example = "Ibis")
    private String nome;
    @NotEmpty
    @Schema(description = "Nome da cidade", example = "Porto Alegre")
    private String cidade;
    @NotEmpty
    @Size(min = 10, max = 14)
    @Schema(description = "Número de telefone do hotel", example = "5135428190")
    private String telefone;
    @NotNull
    @Max(5)
    @Schema(description = "Classificação do hotel em estrelas, de 1 a 5", example = "5")
    private Integer classificacao;
    @Schema(description = "Lista de quarto do hotel (Número dos quartos)", example = "201, 305, 402")
    private List<Quarto> quartos;

}
