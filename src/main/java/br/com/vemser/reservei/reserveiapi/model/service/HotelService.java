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

    //TODO: ARRUMAR O TIPO DE RETORNO
    public List<HotelDTO> getAll() throws SQLException {
        return hotelRepository.getAll().stream()
                .map(hotel -> objectMapper.convertValue(hotel, HotelDTO.class))
                .toList();
    }

    public HotelDTO adicionar(HotelCreateDTO hotel) throws BancoDeDadosException {
        try{
            Hotel hotelEntity = objectMapper.convertValue(hotel,Hotel.class);
            hotelRepository.adicionar(hotelEntity);
            HotelDTO hotelDTO = objectMapper.convertValue(hotelEntity,HotelDTO.class);
            System.out.println("Hotel adicionado com sucesso!");
            return hotelDTO;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        }
    }

    public HotelDTO editar(Integer id, HotelCreateDTO hotelCreateDTO) throws SQLException {
        Hotel hotelEntity = objectMapper.convertValue(hotelCreateDTO,Hotel.class);
        findById(id);
        hotelRepository.editar(id,hotelEntity);
        return objectMapper.convertValue(hotelEntity,HotelDTO.class);
    }

    public void remover(Integer id) throws BancoDeDadosException,SQLException {
        findById(id);
        hotelRepository.remover(id);
    }

    public Hotel findById(Integer id) throws BancoDeDadosException, SQLException {
        return hotelRepository.getAll().stream()
                .filter(x -> x.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new BancoDeDadosException("Hotel não encontrado"));
    }
}
