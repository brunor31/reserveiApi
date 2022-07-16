package br.com.vemser.reservei.reserveiapi.model.repository;

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
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class HotelRepository {

    @Autowired
    private Connection connection;
    @Autowired
    private ObjectMapper objectMapper;
    public List<Hotel> getAll() throws BancoDeDadosException {
        List<Hotel> hoteis = new ArrayList<>();
        ResultSet res;
        try {

            Statement stmt = connection.createStatement();

            String sql = "SELECT h.ID_HOTEL " +
                    "       FROM HOTEL h ";

            res = stmt.executeQuery(sql);

            while (res.next()) {
                Hotel hotel = getIdHotelFromResultSet(res);
                String sql2 = "SELECT h.*,a.* FROM HOTEL h INNER JOIN QUARTO a ON (h.ID_HOTEL = a.ID_HOTEL) WHERE h.ID_HOTEL = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1,hotel.getIdHotel());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Hotel hotel1 = getHotelFromResultSet(resultSet);
                    hoteis.add(hotel1);
                }
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

    private Hotel getIdHotelFromResultSet(ResultSet res) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        return hotel;
    }

    private Hotel getHotelFromResultSet(ResultSet res) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(res.getInt("ID_HOTEL"));
        hotel.setNome(res.getString("NOME"));
        hotel.setCidade(res.getString("CIDADE"));
        hotel.setTelefone(res.getString("TELEFONE"));
        hotel.setClassificacao(res.getInt("CLASSIFICACAO"));
        Quarto quarto = new Quarto();
        List<Quarto> quartos = new ArrayList<>();
        quarto.setIdQuarto(res.getInt("ID_QUARTO"));
        quarto.setNumero(res.getInt("NUMERO"));
        quarto.setTipo(TipoQuarto.ofType(res.getInt("TIPO")));
        quarto.setPrecoDiaria(res.getDouble("PRECO_DIARIA"));
        quartos.add(quarto);
        hotel.setQuartos(quartos);
        return hotel;
    }
}
