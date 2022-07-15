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

    public List<Hotel> getAll() throws BancoDeDadosException {
        List<Hotel> hoteis = new ArrayList<>();
        Connection con = null;
        ResultSet res;
        try {
            con = ConexaoDB.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT h.*, a.NUMERO " +
                    "       FROM HOTEL h " +
                    " INNER JOIN QUARTO a ON (a.ID_HOTEL = h.ID_HOTEL) ";

            res = stmt.executeQuery(sql);

            while (res.next()) {
                Hotel hotel = getQuartoFromResultSet(res);
                hoteis.add(hotel);
            }
            return hoteis;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Hotel getQuartoFromResultSet(ResultSet res) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        hotel.setNome(res.getString("NOME"));
        hotel.setCidade(res.getString("CIDADE"));
        hotel.setTelefone(res.getString("TELEFONE"));
        hotel.setClassificacao(res.getInt("CLASSIFICACAO"));
        hotel.setQuartos(quartoRepository.getAll().stream().
                filter(quarto -> quarto.getHotel().equals(hotel)).toList());
        return hotel;
    }

}
