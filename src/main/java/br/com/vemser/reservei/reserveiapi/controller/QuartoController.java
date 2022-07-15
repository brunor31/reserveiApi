package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.QuartoCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.QuartoDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.service.QuartoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quarto")
@Validated
@Slf4j
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public List<QuartoDTO> getAll() throws BancoDeDadosException {
        return quartoService.getAll();
    }

    @PostMapping
    public QuartoDTO post(@RequestBody QuartoCreateDTO quartoCreateDTO) throws BancoDeDadosException {
        return quartoService.post(quartoCreateDTO);
    }
}
