package br.com.springboot.lojaapp.dto;

import br.com.springboot.lojaapp.model.Cliente;
import br.com.springboot.lojaapp.utils.validation.AtualizarCliente;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@AtualizarCliente
public record ClienteDto(
        UUID id,
        @NotEmpty(message = "campo obrigatorio")
        @Length(min = 5, max = 120)
        String nome,
        @NotEmpty(message = "campo obrigatorio")
        @Email(message = "e-mail invalido")
        String email
) implements Serializable {

    public ClienteDto(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
}
