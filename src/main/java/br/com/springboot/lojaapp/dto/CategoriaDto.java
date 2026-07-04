package br.com.springboot.lojaapp.dto;

import br.com.springboot.lojaapp.model.Categoria;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

public record CategoriaDto(
        UUID id,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "o tamanho deve ser entre 5 e 80 caracteres")
        String nome
) implements Serializable {

    public CategoriaDto(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}
