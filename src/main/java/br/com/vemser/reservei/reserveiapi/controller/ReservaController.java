package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.ReservaCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ReservaDTO;
import br.com.vemser.reservei.reserveiapi.model.service.ReservaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/reserva")
@Validated
@Slf4j
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<ReservaDTO> getAll() throws SQLException {
        return reservaService.getAll();
    }

    @PostMapping
    public ReservaDTO post(@RequestBody @Valid ReservaCreateDTO reservaCreateDTO) throws SQLException {
        return reservaService.post(reservaCreateDTO);
    }
}
