package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.HotelCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.HotelDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public List<HotelDTO> getAll() throws SQLException {
        return hotelRepository.getAll().stream()
                .map(hotel -> objectMapper.convertValue(hotel, HotelDTO.class))
                .toList();
    }

    public HotelDTO post(HotelCreateDTO hotel) throws BancoDeDadosException {
        try{
            Hotel hotelEntity = objectMapper.convertValue(hotel,Hotel.class);
            hotelRepository.post(hotelEntity);
            HotelDTO hotelDTO = objectMapper.convertValue(hotelEntity,HotelDTO.class);
            System.out.println("Hotel adicionado com sucesso!");
            return hotelDTO;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        }
    }

    public HotelDTO put(Integer id, HotelCreateDTO hotelCreateDTO) throws SQLException {
        hotelRepository.getById(id);
        Hotel hotelEntity = objectMapper.convertValue(hotelCreateDTO,Hotel.class);
        hotelRepository.put(id,hotelEntity);
        return objectMapper.convertValue(hotelEntity,HotelDTO.class);
    }

    public void delete(Integer id) throws SQLException {
        hotelRepository.getById(id);
        hotelRepository.delete(id);
    }
}
