package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.HotelDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public List<HotelDTO> getAll() throws BancoDeDadosException {
        return hotelRepository.getAll().stream()
                .map(hotel -> objectMapper.convertValue(hotel, HotelDTO.class))
                .toList();
    }

    public Hotel findById(Integer id) throws BancoDeDadosException {
        return hotelRepository.getAll().stream()
                .filter(x -> x.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new BancoDeDadosException("Hotel não encontrado"));
    }
}
