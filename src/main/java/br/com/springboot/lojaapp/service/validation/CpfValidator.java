package br.com.springboot.lojaapp.service.validation;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            // obrigatoriedade fica a cargo do @NotEmpty
            return true;
        }

        String digitos = value.replaceAll("\\D", "");

        try {
            new CPFValidator().assertValid(digitos);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }
}
