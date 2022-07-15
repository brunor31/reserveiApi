package br.com.vemser.reservei.reserveiapi.model.exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

    public BancoDeDadosException(String msg) {
        super(msg);
    }
}
