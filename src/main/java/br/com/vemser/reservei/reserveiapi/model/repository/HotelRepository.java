package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
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
        ResultSet res1;
        Connection connection = conexaoDB.getConnection();
        try {

            Statement stmt = connection.createStatement();

            String sql = "SELECT * " +
                    "       FROM HOTEL h ";

            res = stmt.executeQuery(sql);

            Statement st = connection.createStatement();

            while (res.next()) {

                Hotel hotel = getHotelFromResultSet(res);

                String select = " SELECT a.NUMERO " +
                        "           FROM QUARTO a " +
                        "          INNER JOIN HOTEL h ON (a.ID_HOTEL = h.ID_HOTEL) " +
                        "          WHERE a.ID_HOTEL = " + hotel.getIdHotel();

                res1 = st.executeQuery(select);

                List<Integer> numeroQuartos = new ArrayList<>();

                while (res1.next()) {
                    Quarto quarto = getQuartosFromResultSet(res1);
                    Integer numero = quarto.getNumero();
                    numeroQuartos.add(numero);
                }

                hotel.setQuartos(numeroQuartos);

                hoteis.add(hotel);
            }

            return hoteis;
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

    public Hotel post(Hotel hotel) throws BancoDeDadosException, SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            Integer proximoId = this.getProximoId(connection);
            hotel.setIdHotel(proximoId);

            String sql = "INSERT INTO HOTEL\n " +
                    "(ID_HOTEL, NOME, CIDADE, TELEFONE, CLASSIFICACAO)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, hotel.getIdHotel());
            stmt.setString(2, hotel.getNome());
            stmt.setString(3, hotel.getCidade());
            stmt.setString(4, hotel.getTelefone());
            stmt.setInt(5, hotel.getClassificacao());

            stmt.executeUpdate();
            return hotel;

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

    public Hotel put(Integer id, Hotel hotel) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE HOTEL SET");
            hotel.toString();
            if (hotel != null) {
                if (hotel.getIdHotel() != null) sql.append(" id_hotel = ?,");
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

    public void delete(Integer id) throws  SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            String sql = "DELETE FROM HOTEL" +
                    "      WHERE ID_HOTEL = ?";

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

    public Hotel getById(Integer id) throws SQLException {
        Hotel hotel = null;
        Connection connection = conexaoDB.getConnection();
        ResultSet res;
        try {

            String sql = "SELECT * " +
                    "       FROM HOTEL h" +
                    "      WHERE h.ID_HOTEL = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if (res.next()) {
                hotel = getHotelFromResultSet(res);
            }

            return hotel;

        } catch (SQLException e) {
            throw new BancoDeDadosException("Hotel n??o encontrado");
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


    private Hotel getHotelFromResultSet(ResultSet res) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        hotel.setNome(res.getString("NOME"));
        hotel.setCidade(res.getString("CIDADE"));
        hotel.setTelefone(res.getString("TELEFONE"));
        hotel.setClassificacao(res.getInt("CLASSIFICACAO"));
        return hotel;
    }

    private Quarto getQuartosFromResultSet(ResultSet res) throws SQLException {
        Quarto quarto = new Quarto();
        quarto.setNumero(res.getInt("NUMERO"));
        return quarto;
    }
}
