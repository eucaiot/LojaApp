package br.com.springboot.lojaapp.model;

import br.com.caelum.stella.validation.CPFValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record Cpf(
        @Column(name = "cpf", unique = true, length = 11)
        String valor
) implements Serializable {

    public Cpf {
        if (valor != null) {
            valor = valor.replaceAll("\\D", "");
            new CPFValidator().assertValid(valor);
        }
    }
}
