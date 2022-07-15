package br.com.vemser.reservei.reserveiapi.controller;

import br.com.vemser.reservei.reserveiapi.model.dto.HotelDTO;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@Validated
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<HotelDTO> getAll() throws BancoDeDadosException {
        return hotelService.getAll();
    }
}
