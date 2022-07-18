package br.com.vemser.reservei.reserveiapi.model.repository;

import br.com.vemser.reservei.reserveiapi.config.ConexaoDB;
import br.com.vemser.reservei.reserveiapi.model.entitys.Quarto;
import br.com.vemser.reservei.reserveiapi.model.entitys.TipoQuarto;
import br.com.vemser.reservei.reserveiapi.model.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuartoRepository {

    @Autowired
    private ConexaoDB conexaoDB;

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

            String sql = "INSERT INTO QUARTO\n " +
                    "(ID_QUARTO, ID_HOTEL, NUMERO, TIPO, PRECO_DIARIA)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, quarto.getIdQuarto());
            stmt.setInt(2, quarto.getIdHotel());
            stmt.setInt(3, quarto.getNumero());
            stmt.setInt(4, quarto.getTipoQuarto().getTipo());
            stmt.setDouble(5, quarto.getPrecoDiaria());

            stmt.executeUpdate();
            return quarto;

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

    public Quarto put(Integer id, Quarto quarto) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE QUARTO SET");
            quarto.toString();
            if (quarto != null) {
                if (quarto.getIdQuarto() != null) sql.append(" id_quarto = ?,");
            }
            if (quarto.getIdHotel() != null) {
                sql.append(" id_hotel = ?,");
            }
            if (quarto.getNumero() != null) {
                sql.append(" numero = ?,");
            }
            if (quarto.getTipoQuarto() != null) {
                sql.append(" tipo = ?,");
            }
            if (quarto.getPrecoDiaria() != null) {
                sql.append(" preco_diaria = ?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_quarto = ? ");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            int index = 1;
            if (quarto != null) {
                if (quarto.getIdQuarto() != null) {
                    stmt.setInt(index++, quarto.getIdQuarto());
                }
            }
            if (quarto.getIdHotel() != null) {
                stmt.setInt(index++, quarto.getIdHotel());
            }
            if (quarto.getNumero() != null) {
                stmt.setInt(index++, quarto.getNumero());
            }
            if (quarto.getTipoQuarto() != null) {
                stmt.setInt(index++, quarto.getTipoQuarto().getTipo());
            }
            if (quarto.getPrecoDiaria() != null) {
                stmt.setDouble(index++, quarto.getPrecoDiaria());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return quarto;
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


    public void delete(Integer id) throws SQLException {
        Connection connection = conexaoDB.getConnection();
        try {

            String sql = "DELETE FROM QUARTO " +
                    "      WHERE ID_QUARTO = ?";

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

    public List<Quarto> getAll() throws SQLException {
        Connection connection = conexaoDB.getConnection();
        List<Quarto> quartos = new ArrayList<>();
        ResultSet res;
        try {
            Statement stmt = connection.createStatement();

            String sql = "SELECT a.*, h.ID_HOTEL" +
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

    public Quarto getById(Integer id) throws SQLException {
        Quarto quarto = null;
        Connection connection = conexaoDB.getConnection();
        ResultSet res;
        try {

            String sql = "SELECT * " +
                    "       FROM QUARTO q" +
                    "      WHERE q.ID_QUARTO = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if (res.next()) {
                quarto = getQuartoFromResultSet(res);
            }

            return quarto;

        } catch (SQLException e) {
            throw new BancoDeDadosException("Quarto não encontrado");
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
        quarto.setIdHotel(res.getInt("ID_HOTEL"));
        quarto.setNumero(res.getInt("NUMERO"));
        quarto.setTipoQuarto(TipoQuarto.ofTipo(res.getInt("TIPO")));
        quarto.setPrecoDiaria(res.getDouble("PRECO_DIARIA"));
        return quarto;
    }
}
