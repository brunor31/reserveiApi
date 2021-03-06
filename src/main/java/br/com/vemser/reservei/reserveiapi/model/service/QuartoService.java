package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.QuartoCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.QuartoDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import br.com.vemser.reservei.reserveiapi.model.repository.HotelRepository;
import br.com.vemser.reservei.reserveiapi.model.repository.QuartoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public List<QuartoDTO> getAll() throws SQLException {
        return quartoRepository.getAll().stream()
                .map(quarto -> objectMapper.convertValue(quarto, QuartoDTO.class))
                .toList();
    }
    public QuartoDTO post(QuartoCreateDTO quartoCreateDTO) throws SQLException {
        Quarto quarto = objectMapper.convertValue(quartoCreateDTO, Quarto.class);
        quartoRepository.post(quarto);
        return objectMapper.convertValue(quarto, QuartoDTO.class);
    }
    public QuartoDTO put(Integer id, QuartoCreateDTO quartoCreateDTO) throws SQLException {
        quartoRepository.getById(id);
        Quarto quartoEntity = objectMapper.convertValue(quartoCreateDTO, Quarto.class);
        quartoRepository.put(id, quartoEntity);
        return objectMapper.convertValue(quartoEntity, QuartoDTO.class);
    }

    public void delete(Integer id) throws SQLException {
        quartoRepository.getById(id);
        quartoRepository.delete(id);
    }
}
