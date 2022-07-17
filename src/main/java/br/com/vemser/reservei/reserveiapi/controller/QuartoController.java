package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.QuartoCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.QuartoDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import br.com.vemser.reservei.reserveiapi.model.service.QuartoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/quarto")
@Validated
@Slf4j
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public List<QuartoDTO> getAll() throws SQLException {
        return quartoService.getAll();
    }

    @PostMapping
    public QuartoDTO post(@RequestBody @Valid QuartoCreateDTO quartoCreateDTO) throws SQLException {
        return quartoService.post(quartoCreateDTO);
    }

    @DeleteMapping("/{idQuarto}")
    public void delete(@PathVariable("idQuarto") Integer id) throws SQLException {
        quartoService.delete(id);
    }
}
