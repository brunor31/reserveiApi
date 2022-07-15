package br.com.vemser.reservei.reserveiapi.model.entitys;

import lombok.Data;

@Data
public class Cliente {

    private Integer idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String senha;
}
