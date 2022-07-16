package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.model.entitys.Hotel;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelRepository {

    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private Connection connection;

    public List<Hotel> getAll() throws BancoDeDadosException {
        List<Hotel> hoteis = new ArrayList<>();
        ResultSet res;
        try {

            Statement stmt = connection.createStatement();

            String sql = "SELECT h.*" +
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
