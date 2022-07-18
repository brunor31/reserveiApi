package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.Cliente;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import br.com.vemser.reservei.reserveiapi.model.exceptions.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private ConexaoDB conexaoDB;

    //gerar Id
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_CLIENTE.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        }
    }

    //criar cliente
    public Cliente post(Cliente cliente) throws SQLException, RegraDeNegocioException {
        Connection connection = conexaoDB.getConnection();
        try {
            Integer proximoId = this.getProximoId(connection);
            cliente.setIdCliente(proximoId);

            String sql = "INSERT INTO CLIENTE" +
                    "(ID_CLIENTE, NOME, CPF, TELEFONE, EMAIL, SENHA)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getSenha());

            stmt.executeUpdate();
            return cliente;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Cliente> getAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        ResultSet res;
        Connection connection = conexaoDB.getConnection();
        try {
            Statement stmt = connection.createStatement();

            String sql = "SELECT c.* " +
                    "       FROM Cliente c";

            res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = getClienteFromResultSet(res);
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente getById(Integer id) throws SQLException {
        Cliente cliente = null;
        Connection connection = conexaoDB.getConnection();
        ResultSet res;
        try {

            String sql = "SELECT c.* " +
                    "       FROM CLIENTE c " +
                    "      WHERE c.ID_CLIENTE = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if (res.next()){
                cliente = getClienteFromResultSet(res);
            }

            return cliente;

        } catch (SQLException e) {
            throw new BancoDeDadosException("Cliente não encontrado");
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente getByCpf(String cpf) throws SQLException {
        Cliente cliente = null;
        Connection connection = conexaoDB.getConnection();
        ResultSet res;
        try {

            String sql = "SELECT c.* " +
                    "       FROM CLIENTE c " +
                    "      WHERE c.CPF = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);

            res = stmt.executeQuery();

            if(res.next()){
                cliente = getClienteFromResultSet(res);
            }

            return cliente;

        } catch (SQLException e) {
            throw new BancoDeDadosException("Cliente não encontrado");
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente put(Integer id, Cliente cliente) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE cliente SET");
            cliente.toString();
            if (cliente != null) {
                if (cliente.getIdCliente() != null)
                    sql.append(" id_cliente = ?,");
            }
            if (cliente.getNome() != null) {
                sql.append(" nome = ?,");
            }
            if (cliente.getCpf() != null) {
                sql.append(" cpf = ?,");
            }
            if (cliente.getTelefone() != null) {
                sql.append(" telefone = ?,");
            }
            if (cliente.getEmail() != null) {
                sql.append(" email = ?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_cliente = ? ");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            int index = 1;
            if (cliente != null) {
                if (cliente.getIdCliente() != null) {
                    stmt.setInt(index++, cliente.getIdCliente());
                }
            }
            if (cliente.getNome() != null) {
                stmt.setString(index++, cliente.getNome());
            }
            if (cliente.getCpf() != null) {
                stmt.setString(index++, cliente.getCpf());
            }
            if (cliente.getTelefone() != null) {
                stmt.setString(index++, cliente.getTelefone());
            }
            if (cliente.getEmail() != null) {
                stmt.setString(index++, cliente.getEmail());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return cliente;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Integer id) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            String sql = "DELETE FROM CLIENTE " +
                    "      WHERE ID_CLIENTE = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeQuery();
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Cliente getClienteFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("ID_CLIENTE"));
        cliente.setNome(res.getString("NOME"));
        cliente.setCpf(res.getString("CPF"));
        cliente.setTelefone(res.getString("TELEFONE"));
        cliente.setEmail(res.getString("EMAIL"));
        cliente.setSenha(res.getString("SENHA"));
        return cliente;
    }
}
