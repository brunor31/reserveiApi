package br.com.vemser.reservei.reserveiapi.model.entitys;

import java.util.Arrays;

public enum TipoReserva {

    RESERVA_COMUM(1), RESERVA_PREMIUM(2);

    private Integer tipo;

    TipoReserva(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoReserva ofTipo(Integer tipo) {
        return Arrays.stream(TipoReserva.values()).filter(tp -> tp.getTipo().equals(tipo)).findFirst().get();
    }

    public String tipoString() {
        if (tipo == 1) {
            return "Reserva Comum";
        } else return "Reserva Premium";
    }
}
