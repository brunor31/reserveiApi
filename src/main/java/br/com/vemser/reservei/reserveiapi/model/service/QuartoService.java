package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.QuartoCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.QuartoDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.repository.QuartoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ObjectMapper objectMapper;

    public QuartoDTO post(QuartoCreateDTO quartoCreateDTO) throws BancoDeDadosException {
        Quarto quarto = objectMapper.convertValue(quartoCreateDTO, Quarto.class);
        quartoRepository.post(quarto);
        return objectMapper.convertValue(quarto, QuartoDTO.class);
    }
    public List<QuartoDTO> getAll() throws BancoDeDadosException {
        return quartoRepository.getAll().stream()
                .map(quarto -> objectMapper.convertValue(quarto, QuartoDTO.class))
                .toList();
    }
}
