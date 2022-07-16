package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class HotelRepository {

    @Autowired
    private ConexaoDB conexaoDB;
    @Autowired
    private ObjectMapper objectMapper;
    public List<Hotel> getAll() throws BancoDeDadosException, SQLException {
        List<Hotel> hoteis = new ArrayList<>();
        ResultSet res;
        Connection connection = conexaoDB.getConnection();
        try {

            Statement stmt = connection.createStatement();

            String sql = "SELECT * " +
                    "       FROM HOTEL h ";

            res = stmt.executeQuery(sql);

            while (res.next()) {
                Hotel hotel = getHotelFromResultSet(res);
                hoteis.add(hotel);
            }

            return hoteis;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
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

    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_HOTEL.nextval mysequence from DUAL";
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

    public Hotel adicionar(Hotel hotel) throws BancoDeDadosException,SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            Integer proximoId = this.getProximoId(connection);
            hotel.setIdHotel(proximoId);

            String sql = "INSERT INTO HOTEL\n" +
                    "(ID_HOTEL, NOME, CIDADE, TELEFONE, CLASSIFICACAO)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, hotel.getIdHotel());
            stmt.setString(2, hotel.getNome());
            stmt.setString(3, hotel.getCidade());
            stmt.setString(4, hotel.getTelefone());
            stmt.setInt(5, hotel.getClassificacao());

            int res = stmt.executeUpdate();
            System.out.println("adicionarHotel.res=" + res);
            return hotel;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
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

    public Hotel editar (Integer id, Hotel hotel) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try{
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE HOTEL SET");
            hotel.toString();
            if (hotel != null) {
                if (hotel.getIdHotel() != null)
                    sql.append(" id_hotel = ?,");
            }
            if (hotel.getNome() != null) {
                sql.append(" nome = ?,");
            }
            if (hotel.getCidade() != null) {
                sql.append(" cidade = ?,");
            }
            if (hotel.getTelefone() != null) {
                sql.append(" telefone = ?,");
            }
            if (hotel.getClassificacao() != null) {
                sql.append(" classificacao = ?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_hotel = ? ");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            int index = 1;
            if (hotel != null) {
                if (hotel.getIdHotel() != null) {
                    stmt.setInt(index++, hotel.getIdHotel());
                }
            }
            if (hotel.getNome() != null) {
                stmt.setString(index++, hotel.getNome());
            }
            if (hotel.getCidade() != null) {
                stmt.setString(index++, hotel.getCidade());
            }
            if (hotel.getTelefone() != null) {
                stmt.setString(index++, hotel.getTelefone());
            }
            if (hotel.getClassificacao() != null) {
                stmt.setInt(index++, hotel.getClassificacao());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return hotel;
        }catch (SQLException e){
            throw new BancoDeDadosException(e.getMessage());
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void remover(Integer id) throws BancoDeDadosException,SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            String sql = "DELETE FROM HOTEL WHERE ID_HOTEL = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeQuery();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
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

    private Hotel getHotelFromResultSet(ResultSet res) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        hotel.setNome(res.getString("NOME"));
        hotel.setCidade(res.getString("CIDADE"));
        hotel.setTelefone(res.getString("TELEFONE"));
        hotel.setClassificacao(res.getInt("CLASSIFICACAO"));
        return hotel;
    }
}
