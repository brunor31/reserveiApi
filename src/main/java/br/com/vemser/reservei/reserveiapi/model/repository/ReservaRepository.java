package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.*;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservaRepository {

    @Autowired
    private ConexaoDB conexaoDB;
    @Autowired
    private ObjectMapper objectMapper;

    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_RESERVA.nextval mysequence from DUAL";
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

    public List<Reserva> getAll() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        ResultSet res;
        Connection connection = conexaoDB.getConnection();
        try {

            Statement stmt = connection.createStatement();

            String sql = "SELECT r.* " +
                    "       FROM Reserva r ";

            res = stmt.executeQuery(sql);

            Statement st = connection.createStatement();

            while (res.next()) {
                Reserva reserva = getReservaFromResultSet(res);
                reservas.add(reserva);
            }

            return reservas;
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

    public Reserva getById(Integer id) throws SQLException {
        Reserva reserva = null;
        Connection connection = conexaoDB.getConnection();
        ResultSet res;
        try {

            String sql = "SELECT * " +
                    "       FROM RESERVA r" +
                    "      WHERE r.ID_RESERVA = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if (res.next()) {
                reserva = getReservaFromResultSet(res);
            }

            return reserva;

        } catch (SQLException e) {
            throw new BancoDeDadosException("Reserva não encontrada");
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


    public Reserva post(Reserva reserva) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            Integer proximoId = this.getProximoId(connection);
            reserva.setIdReserva(proximoId);

            String sql = "INSERT INTO RESERVA\n " +
                    "(ID_RESERVA, ID_HOTEL, ID_QUARTO, ID_CLIENTE, DATA_ENTRADA, DATA_SAIDA, TIPO, VALOR_RESERVA)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, reserva.getIdReserva());
            stmt.setInt(2, reserva.getIdHotel());
            stmt.setInt(3, reserva.getIdQuarto());
            stmt.setInt(4, reserva.getIdCliente());
            stmt.setDate(5, Date.valueOf(reserva.getDataEntrada()));
            stmt.setDate(6, Date.valueOf(reserva.getDataSaida()));
            stmt.setInt(7, reserva.getTipo().getTipo());
            stmt.setDouble(8, reserva.getValorReserva());

            int res = stmt.executeUpdate();

            return reserva;
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

    public Reserva put(Integer id, Reserva reserva) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE RESERVA SET");
            reserva.toString();
            if (reserva != null) {
                if (reserva.getIdReserva() != null) sql.append(" id_reserva = ?,");
            }
            if (reserva.getIdHotel() != null) {
                sql.append(" id_hotel = ?,");
            }
            if (reserva.getIdQuarto() != null) {
                sql.append(" id_quarto = ?,");
            }
            if (reserva.getIdCliente() != null) {
                sql.append(" id_cliente = ?,");
            }
            if (reserva.getDataEntrada() != null) {
                sql.append(" data_entrada = ?,");
            }
            if (reserva.getDataSaida() != null) {
                sql.append(" data_saida = ?,");
            }
            if (reserva.getTipo() != null) {
                sql.append(" tipo = ?,");
            }
            if (reserva.getValorReserva() != null){
                sql.append(" valor_reserva = ? ");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_reserva = ? ");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            int index = 1;
            if (reserva != null) {
                if (reserva.getIdReserva() != null) {
                    stmt.setInt(index++, reserva.getIdReserva());
                }
            }
            if (reserva.getIdHotel() != null) {
                stmt.setInt(index++, reserva.getIdHotel());
            }
            if (reserva.getIdQuarto() != null) {
                stmt.setInt(index++, reserva.getIdQuarto());
            }
            if (reserva.getIdCliente() != null) {
                stmt.setInt(index++, reserva.getIdCliente());
            }
            if (reserva.getDataEntrada() != null) {
                stmt.setDate(index++, Date.valueOf(reserva.getDataEntrada()));
            }
            if (reserva.getDataEntrada() != null) {
                stmt.setDate(index++, Date.valueOf(reserva.getDataSaida()));
            }
            if (reserva.getTipo() != null) {
                stmt.setInt(index++, reserva.getTipo().getTipo());
            }
            if (reserva.getValorReserva() != null) {
                stmt.setDouble(index++, reserva.getValorReserva());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return reserva;
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

            String sql = "DELETE FROM RESERVA " +
                    "      WHERE ID_RESERVA = ?";

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

    private Reserva getReservaFromResultSet(ResultSet res) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(res.getInt("ID_RESERVA"));
        reserva.setIdHotel(res.getInt("ID_HOTEL"));
        reserva.setIdQuarto(res.getInt("ID_QUARTO"));
        reserva.setIdCliente(res.getInt("ID_CLIENTE"));
        reserva.setDataEntrada(res.getDate("DATA_ENTRADA").toLocalDate());
        reserva.setDataSaida(res.getDate("DATA_SAIDA").toLocalDate());
        reserva.setTipo(TipoReserva.ofTipo(res.getInt("TIPO")));
        reserva.setValorReserva(res.getDouble("VALOR_RESERVA"));
        return reserva;
    }
}
