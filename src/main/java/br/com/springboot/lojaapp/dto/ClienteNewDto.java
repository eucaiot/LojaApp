package br.com.springboot.lojaapp.dto;

import br.com.springboot.lojaapp.service.validation.InserirCliente;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@InserirCliente
public record ClienteNewDto(
        @NotEmpty(message = "Campo Obrigatorio")
        @Size(min = 5, max = 120)
        String nome,
        @NotEmpty(message = "Campo Obrigatorio")
        @Email(message = "e-mail inválido")
        String email,
        @NotEmpty(message = "preenchimento obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,
        String logradouro,
        Integer numero,
        String complemento,
        String bairro,
        String cep,
        @NotEmpty(message = "campo obrigatorio")
        String telefone1,
        String telefone2,
        String telefone3,
        UUID cidadeId
) implements Serializable {
}
