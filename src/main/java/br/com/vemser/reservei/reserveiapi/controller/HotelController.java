package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.HotelCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.HotelDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;

@ApiResponses({
        @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
        @ApiResponse(responseCode = "404", description = "Hotel não encontrado"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
})
@RestController
@RequestMapping("/hotel")
@Validated
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ObjectMapper objectMapper;

    @Operation(summary = "Listar todos os hoteis", description = "Retorna uma lista de hoteis")
    @ApiResponse(responseCode = "200", description = "Retorna uma lista de hoteis")
    @GetMapping
    public ResponseEntity<HotelDTO> getAll() throws SQLException {
        return new ResponseEntity(hotelService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Criar um hotel", description = "Cria um hotel e adiciona no banco")
    @ApiResponse(responseCode = "200", description = "Cria um hotel e adiciona no banco")
    @PostMapping
    public ResponseEntity<HotelDTO> adicionar(@RequestBody @Valid HotelCreateDTO hotelCreateDTO) throws BancoDeDadosException {
        return ResponseEntity.ok(hotelService.post(hotelCreateDTO));
    }

    @Operation(summary = "Editar um hotel", description = "Edita um hotel do banco")
    @ApiResponse(responseCode = "200", description = "Edita um hotel do banco")
    @PutMapping("/{idHotel}")
    public ResponseEntity<HotelDTO> editar(@PathVariable("idHotel") Integer id
            ,@RequestBody HotelCreateDTO hotelCreateDTO) throws SQLException {
        return new ResponseEntity(hotelService.put(id, hotelCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Excluir um hotel", description = "Excluí um hotel do banco")
    @ApiResponse(responseCode = "200", description = "Excluí um hotel do banco")
    @DeleteMapping("/{idHotel}")
    public void remover(@PathVariable("idHotel") Integer id) throws SQLException {
        hotelService.delete(id);
    }
}
