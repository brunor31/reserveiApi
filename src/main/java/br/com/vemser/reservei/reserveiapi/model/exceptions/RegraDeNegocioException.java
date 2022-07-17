package br.com.vemser.reservei.reserveiapi.model.exceptions;

import java.sql.SQLException;

public class RegraDeNegocioException extends Exception {

    public RegraDeNegocioException(String message) {
        super(message);
    }
}
