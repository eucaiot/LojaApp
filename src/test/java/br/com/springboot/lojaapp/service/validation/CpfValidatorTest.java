package br.com.springboot.lojaapp.service.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpfValidatorTest {

    private final CpfValidator validator = new CpfValidator();

    // Mesmos CPFs usados no data.sql — garante que a massa passa na validação.
    @ParameterizedTest
    @ValueSource(strings = {
            "11144477735", "52998224725", "39053344705",
            "04252011477", "71286819067", "30828009554"
    })
    void aceitaCpfValido(String cpf) {
        assertTrue(validator.isValid(cpf, null));
    }

    @Test
    void aceitaCpfComMascara() {
        assertTrue(validator.isValid("111.444.777-35", null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678900", "11111111111", "111444777"})
    void rejeitaCpfInvalido(String cpf) {
        assertFalse(validator.isValid(cpf, null));
    }

    @Test
    void valorNuloDelegadoAoNotEmpty() {
        assertTrue(validator.isValid(null, null));
    }
}
