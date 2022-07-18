package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.ReservaCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ReservaDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import br.com.vemser.reservei.reserveiapi.model.service.ReservaService;
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
import java.util.List;

@ApiResponses({
        @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
})
@RestController
@RequestMapping("/reserva")
@Validated
@Slf4j
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Operation(summary = "Listar todas as reservas", description = "Retorna todas as reservas")
    @ApiResponse(responseCode = "200", description = "Retorna todas as reservas")
    @GetMapping
    public List<ReservaDTO> getAll() throws SQLException {
        return reservaService.getAll();
    }
    @Operation(summary = "Lista reserva por id", description = "Retorna uma reserva por id")
    @ApiResponse(responseCode = "200", description = "Retorna uma reserva por id")
    @GetMapping("/{idReserva}")
    public ResponseEntity<ReservaDTO> getById(@PathVariable("idReserva") Integer id) throws SQLException {
        return new ResponseEntity(reservaService.getById(id), HttpStatus.OK);
    }
    @Operation(summary = "Criar uma reserva", description = "Cria uma nova reserva")
    @ApiResponse(responseCode = "200", description = "Cria uma nova reserva")
    @PostMapping
    public ReservaDTO post(@RequestBody @Valid ReservaCreateDTO reservaCreateDTO) throws SQLException, RegraDeNegocioException {
        return reservaService.post(reservaCreateDTO);
    }

    @Operation(summary = "Editar uma reserva", description = "Edita uma reserva")
    @ApiResponse(responseCode = "200", description = "Edita uma reserva")
    @PutMapping("/{idReserva}")
    public ReservaDTO put(@PathVariable("idReserva") Integer id,
                          @RequestBody @Valid ReservaCreateDTO reservaCreateDTO) throws SQLException {
        return reservaService.put(id, reservaCreateDTO);
    }
    @Operation(summary = "Deletar uma reserva", description = "Deleta uma reserva")
    @ApiResponse(responseCode = "200", description = "Deleta uma reserva")
    @DeleteMapping("/{idReserva}")
    public void delete(@PathVariable("idReserva") Integer id) throws SQLException {
        reservaService.delete(id);
    }
}
