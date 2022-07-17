package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.HotelCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.HotelDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RestController
@RequestMapping("/hotel")
@Validated
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<HotelDTO> getAll() throws BancoDeDadosException, SQLException {
        return hotelService.getAll().stream()
                .map(hotel -> objectMapper.convertValue(hotel,HotelDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HotelDTO> adicionar(@RequestBody @Valid HotelCreateDTO hotelCreateDTO) throws BancoDeDadosException {
        return ResponseEntity.ok(hotelService.adicionar(hotelCreateDTO));
    }

    @PutMapping("/{idHotel}")
    public ResponseEntity<HotelDTO> editar(@PathVariable("idHotel") Integer id,@RequestBody HotelCreateDTO hotelCreateDTO) throws SQLException {
        return new ResponseEntity(hotelService.editar(id, hotelCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idHotel}")
    public void remover(@PathVariable("idHotel") Integer id) throws BancoDeDadosException,SQLException {
        hotelService.remover(id);
    }
}
