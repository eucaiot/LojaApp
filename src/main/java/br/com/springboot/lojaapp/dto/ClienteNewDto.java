package br.com.springboot.lojaapp.dto;

import br.com.springboot.lojaapp.service.validation.CPF;
import br.com.springboot.lojaapp.service.validation.InserirCliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

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
        @Valid
        EnderecoNewDto endereco,
        @NotEmpty(message = "campo obrigatorio")
        List<String> telefones
) implements Serializable {
}
