package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteCreateDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
import br.com.vemser.reservei.reserveiapi.model.entitys.Cliente;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import br.com.vemser.reservei.reserveiapi.model.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ObjectMapper objectMapper;
    
    public ClienteDTO post(ClienteCreateDTO clienteCreateDTO) throws SQLException, RegraDeNegocioException {
        Cliente cliente = clienteRepository.getByCpf(clienteCreateDTO.getCpf());
        if (cliente != null){
        throw new BancoDeDadosException("Cliente já possui cadastro");
        } else {
            cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
            clienteRepository.post(cliente);
            return objectMapper.convertValue(cliente, ClienteDTO.class);
        }
    }
    
    public List<ClienteDTO> getAll() throws SQLException {
        return clienteRepository.getAll().stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();
    }
    
    public ClienteDTO getById(Integer idCliente) throws SQLException {
        return objectMapper.convertValue(clienteRepository.getById(idCliente), ClienteDTO.class);
    }
    
    public ClienteDTO getByCpf(String cpf) throws SQLException {
        return objectMapper.convertValue(clienteRepository.getByCpf(cpf), ClienteDTO.class);
    }
    public ClienteDTO put(Integer id, ClienteCreateDTO clienteCreateDTO) throws SQLException {
        clienteRepository.getById(id);
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        clienteRepository.put(id, cliente);
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    public void delete(Integer id) throws SQLException {
        clienteRepository.getById(id);
        clienteRepository.delete(id);
    }
}
    
    
    

