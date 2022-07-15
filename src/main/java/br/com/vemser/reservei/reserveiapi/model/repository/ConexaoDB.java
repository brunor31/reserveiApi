package br.com.vemser.reservei.reserveiapi.model.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Repository
public class ConexaoDB {

    private static final String SERVER = "vemser-dbc.dbccompany.com.br";

    private static final String PORT = "25000";

    private static final String DATABASE = "xe";

    private static final String USER = "EQUIPE_5";

    private static final String PASS = "GaMXtyWKOB";

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        Connection con = DriverManager.getConnection(url, USER, PASS);

        con.createStatement().execute("alter session set current_schema=EQUIPE_5");

        return con;
    }
}
