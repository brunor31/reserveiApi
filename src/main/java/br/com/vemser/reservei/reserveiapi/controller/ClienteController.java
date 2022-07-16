package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
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


@RestController
@RequestMapping("/cliente")
@Validated
@Slf4j
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    //criar Cliente
    @Operation(summary = "Cria Cliente", description = "Cria um Cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente criado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteDTO>  post(@RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.post(clienteCreateDTO), HttpStatus.OK);
    }
    
    //listar todos os clientes
    @Operation(summary = "Lista Clientes", description = "Lista todos os clientes do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Clientes listados"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<ClienteDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.listar(), HttpStatus.OK);
    }
    
    //listar cliente por id
    @Operation(summary = "Lista Cliente Id", description = "Lista um cliente do banco de dados por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "400", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> pegarClienteId(@PathVariable("idCliente") Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.pegarClienteId(id), HttpStatus.OK) ;
    }
    
    //listar cliente por cpf
    @Operation(summary = "Lista Cliente cpf", description = "Lista um cliente do banco de dados por CPF")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "400", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/cpf")
    public ResponseEntity<ClienteDTO> pegarClienteCpf(@RequestParam("cpf") String cpf) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.pegarClienteCpf(cpf), HttpStatus.OK);
    }
    
    //editar cliente
    @Operation(summary = "Atualiza Cliente Id", description = "Atualiza um cliente do banco de dados por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente Atualizado"),
                    @ApiResponse(responseCode = "400", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable("idCliente") Integer id,@RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity(clienteService.atualizar(id, clienteCreateDTO), HttpStatus.OK) ;
    }
    
    //deletar cliente
    @Operation(summary = "Remove Cliente Id", description = "Remove um cliente do banco de dados por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "400", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCliente}")
    public void remover(@PathVariable("idCliente") Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        clienteService.remover(id);
    }
}
