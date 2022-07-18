package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import br.com.vemser.reservei.reserveiapi.model.service.ClienteService;
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
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
})
@RestController
@RequestMapping("/cliente")
@Validated
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Criar um cliente", description = "Cria um cliente e adiciona no banco")
    @ApiResponse(responseCode = "200", description = "Cria um cliente e adiciona no banco")
    @PostMapping
    public ResponseEntity<ClienteDTO> post(@RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws SQLException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.post(clienteCreateDTO), HttpStatus.OK);
    }
    @Operation(summary = "Lista todos os cliente", description = "Retorna uma lista com todos os clientes")
    @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os clientes")
    @GetMapping
    public ResponseEntity<ClienteDTO> getAll() throws SQLException {
        return new ResponseEntity(clienteService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retorna um cliente por Id", description = "Retorna um cliente por Id")
    @ApiResponse(responseCode = "200", description = "Retorna um cliente por Id")
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable("idCliente") Integer idCliente) throws SQLException {
        return new ResponseEntity(clienteService.getById(idCliente), HttpStatus.OK);
    }

    @Operation(summary = "Retorna um cliente por cpf", description = "Retorna um cliente por cpf")
    @ApiResponse(responseCode = "200", description = "Retorna um cliente por cpf")
    @GetMapping("/{cpf}/cpf")
    public ResponseEntity<ClienteDTO> getByCpf(@PathVariable("cpf") String cpf) throws SQLException {
        return new ResponseEntity(clienteService.getByCpf(cpf), HttpStatus.OK);
    }

    @Operation(summary = "Editar um cliente", description = "Edita um cliente")
    @ApiResponse(responseCode = "200", description = "Edita um cliente")
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> put(@PathVariable("idCliente") Integer id
            , @RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws SQLException {
        return new ResponseEntity(clienteService.put(id, clienteCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar um cliente", description = "Deleta um cliente")
    @ApiResponse(responseCode = "200", description = "Deleta um cliente")
    @DeleteMapping("/{idCliente}")
    public void delete(@PathVariable("idCliente") Integer id) throws SQLException {
        clienteService.delete(id);
    }
}
