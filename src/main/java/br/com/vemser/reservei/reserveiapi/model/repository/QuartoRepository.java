package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import br.com.vemser.reservei.reserveiapi.model.entitys.TipoQuarto;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuartoRepository {

    @Autowired
    private ConexaoDB conexaoDB;

    @Autowired
    private ObjectMapper objectMapper;

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_QUARTO.nextval mysequence from DUAL";
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

    public Quarto post(Quarto quarto) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {

            Integer proximoId = this.getProximoId(connection);
            quarto.setIdQuarto(proximoId);

            String sql = "INSERT INTO QUARTO\n" +
                    "(ID_QUARTO, ID_HOTEL, NUMERO, TIPO, PRECO_DIARIA)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, quarto.getIdQuarto());
            stmt.setInt(2, quarto.getHotel().getIdHotel());
            stmt.setInt(3, quarto.getNumero());
            stmt.setInt(4, quarto.getTipo().getTipo());
            stmt.setDouble(5, quarto.getPrecoDiaria());

            stmt.executeUpdate();
            return quarto;

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

            String sql = "DELETE FROM QUARTO WHERE ID_QUARTO = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

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
    public List<Quarto> getAll() throws SQLException {
        Connection connection = conexaoDB.getConnection();
        List<Quarto> quartos = new ArrayList<>();
        ResultSet res;
        try {
            Statement stmt = connection.createStatement();

            String sql = "SELECT a.*, h.ID_HOTEL, h.NOME, h.CIDADE " +
                    "       FROM QUARTO a " +
                    "       FULL JOIN HOTEL h ON h.ID_HOTEL = a.ID_HOTEL";

            res = stmt.executeQuery(sql);

            while (res.next()) {
                Quarto quarto = getQuartoFromResultSet(res);
                quartos.add(quarto);
            }
            return quartos;
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

    private Quarto getQuartoFromResultSet(ResultSet res) throws SQLException {
        Quarto quarto = new Quarto();
        quarto.setIdQuarto(res.getInt("ID_QUARTO"));
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        hotel.setNome(res.getString("NOME"));
        hotel.setCidade(res.getString("CIDADE"));
        quarto.setHotel(hotel);
        quarto.setNumero(res.getInt("NUMERO"));
        quarto.setTipo(TipoQuarto.ofType(res.getInt("TIPO")));
        quarto.setPrecoDiaria(res.getDouble("PRECO_DIARIA"));
        return quarto;    }
}
