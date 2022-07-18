package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.QuartoCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.QuartoDTO;
import br.com.vemser.reservei.reserveiapi.model.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@ApiResponses({
        @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
        @ApiResponse(responseCode = "404", description = "Quarto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
})
@RestController
@RequestMapping("/quarto")
@Validated
@Slf4j
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @Operation(summary = "Listar todos os quartos", description = "Retorna uma lista de quartos")
    @ApiResponse(responseCode = "200", description = "Retorna uma lista de quartos")
    @GetMapping
    public ResponseEntity<QuartoDTO> getAll() throws SQLException {
        return new ResponseEntity(quartoService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Criar um quarto", description = "Cria um novo quarto")
    @ApiResponse(responseCode = "200", description = "Cria um novo quarto")
    @PostMapping
    public ResponseEntity<QuartoDTO> post(@RequestBody @Valid QuartoCreateDTO quartoCreateDTO) throws SQLException {
        return new ResponseEntity(quartoService.post(quartoCreateDTO), HttpStatus.OK);
    }
    @Operation(summary = "Atualizar um quarto", description = "Atualiza um quarto")
    @ApiResponse(responseCode = "200", description = "Atualiza um quarto")
    @PutMapping("/{idQuarto}")
    public ResponseEntity<QuartoDTO> put(@PathVariable("idQuarto") Integer id,
                         @RequestBody @Valid QuartoCreateDTO quartoCreateDTO) throws SQLException {
        return new ResponseEntity(quartoService.put(id, quartoCreateDTO), HttpStatus.OK);
    }
    @Operation(summary = "Deletar um quarto", description = "Deleta um quarto")
    @ApiResponse(responseCode = "200", description = "Deleta um quarto")
    @DeleteMapping("/{idQuarto}")
    public void delete(@PathVariable("idQuarto") Integer id) throws SQLException {
        quartoService.delete(id);
    }
}
