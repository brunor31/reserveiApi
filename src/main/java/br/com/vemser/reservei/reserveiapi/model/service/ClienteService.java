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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public ClienteDTO post(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        clienteRepository.post(cliente);
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }
    
    public List<ClienteDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return clienteRepository.listar().stream().map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
    
    public ClienteDTO pegarClienteId(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = buscarIdCliente(id);
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }
    
    public ClienteDTO pegarClienteCpf(String cpf) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = buscarCpfCliente(cpf);
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }
    
    public ClienteDTO atualizar(Integer id, ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        buscarIdCliente(id);
        clienteRepository.editar(id, cliente);
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }
    
    public void remover(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = buscarIdCliente(id);
        clienteRepository.remover(cliente.getIdCliente());
    }
    
    public Cliente buscarIdCliente( Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = clienteRepository.buscarIdCliente(id)
                .stream()
                .findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("id nao encontrado"));
        return cliente;
    }
    
    public Cliente buscarCpfCliente(String cpf) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = clienteRepository.buscarCpfCliente(cpf)
                .stream()
                .filter(cliente1 -> cliente1.getCpf().contains(cpf))
                .findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("cpf nao"));
        return cliente;
    }
}
    
    
    

