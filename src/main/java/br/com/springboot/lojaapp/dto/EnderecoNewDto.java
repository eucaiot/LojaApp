package br.com.springboot.lojaapp.dto;

import java.io.Serializable;
import java.util.UUID;

public record EnderecoNewDto(
        String logradouro,
        Integer numero,
        String complemento,
        String bairro,
        String cep,
        UUID cidadeId
) implements Serializable {
}
