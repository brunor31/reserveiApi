package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ReservaCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ReservaDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import br.com.vemser.reservei.reserveiapi.model.entitys.Reserva;
import br.com.vemser.reservei.reserveiapi.model.entitys.TipoReserva;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import br.com.vemser.reservei.reserveiapi.model.repository.QuartoRepository;
import br.com.vemser.reservei.reserveiapi.model.repository.ReservaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<ReservaDTO> getAll() throws SQLException {
        return reservaRepository.getAll().stream()
                .map(reserva -> objectMapper.convertValue(reserva, ReservaDTO.class))
                .toList();
    }

    public ReservaDTO getById(Integer id) throws SQLException {
        return objectMapper.convertValue(reservaRepository.getById(id), ReservaDTO.class);
    }

    public ReservaDTO post(ReservaCreateDTO reservaCreateDTO) throws SQLException, RegraDeNegocioException {
        Reserva reservaEntity = objectMapper.convertValue(reservaCreateDTO, Reserva.class);
        reservaEntity.setValorReserva(calcularDiarias(reservaEntity));
        reservaRepository.post(reservaEntity);
        ReservaDTO reservaDTO = objectMapper.convertValue(reservaEntity, ReservaDTO.class);
        ClienteDTO clienteDTO = clienteService.getById(reservaDTO.getIdCliente());
        emailService.sendMail(clienteDTO, reservaDTO);
        return reservaDTO;
    }

    public ReservaDTO put(Integer id, ReservaCreateDTO reservaCreateDTO) throws SQLException {
        reservaRepository.getById(id);
        Reserva reservaEntity = objectMapper.convertValue(reservaCreateDTO, Reserva.class);
        reservaEntity.setValorReserva(calcularDiarias(reservaEntity));
        reservaRepository.put(id, reservaEntity);
        return objectMapper.convertValue(reservaEntity, ReservaDTO.class);
    }

    public void delete(Integer id) throws SQLException {
        reservaRepository.getById(id);
        reservaRepository.delete(id);
    }

    private Double calcularDiarias(Reserva reserva) throws SQLException {
        LocalDate d1 = LocalDate.parse(reserva.getDataEntrada().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse(reserva.getDataSaida().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        long diffDays = diff.toDays();
        Quarto quarto = quartoRepository.getById(reserva.getIdQuarto());
        if (reserva.getTipo() == TipoReserva.RESERVA_PREMIUM) {
            return diffDays * quarto.getPrecoDiaria() * 0.90;
        } else {
            return diffDays * quarto.getPrecoDiaria();
        }
    }
}
