package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
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


@RestController
@RequestMapping("/cliente")
@Validated
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> post(@RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws SQLException {
        return new ResponseEntity(clienteService.post(clienteCreateDTO), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ClienteDTO> getAll() throws SQLException {
        return new ResponseEntity(clienteService.getAll(), HttpStatus.OK);
    }
//    @GetMapping("/{idCliente}")
//    public ResponseEntity<ClienteDTO> getById(@PathVariable("idCliente") Integer idCliente) throws SQLException {
//        return new ResponseEntity(clienteService.getById(idCliente), HttpStatus.OK);
//    }
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> getByCpf(@PathVariable("cpf") String cpf) throws SQLException {
        return new ResponseEntity(clienteService.getByCpf(cpf), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> put(@PathVariable("idCliente") Integer id
            , @RequestBody @Valid ClienteCreateDTO clienteCreateDTO) throws SQLException {
        return new ResponseEntity(clienteService.put(id, clienteCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public void delete(@PathVariable("idCliente") Integer id) throws SQLException {
        clienteService.delete(id);
    }
}
