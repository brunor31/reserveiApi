package br.com.vemser.reservei.reserveiapi.model.entitys;

import java.util.Arrays;

public enum TipoQuarto {

    QUARTO_SOLTEIRO(0), QUARTO_CASAL(1);
    private Integer tipo;

    TipoQuarto(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoQuarto ofType(Integer tipo) {
        return Arrays.stream(TipoQuarto.values()).filter(tp -> tp.getTipo().equals(tipo)).findFirst().get();
    }

    public String tipoString() {
        if (tipo == 1) {
            return "Quarto Solteiro";
        } else return "Quarto Casal";
    }
}

