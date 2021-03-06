package br.com.vemser.reservei.reserveiapi.model.entitys;

import java.util.Arrays;

public enum TipoQuarto {

    QUARTO_SOLTEIRO(1), QUARTO_CASAL(2);
    private Integer tipo;

    TipoQuarto(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoQuarto ofTipo(Integer tipo) {
        return Arrays.stream(TipoQuarto.values()).filter(tp -> tp.getTipo().equals(tipo)).findFirst().get();
    }
}