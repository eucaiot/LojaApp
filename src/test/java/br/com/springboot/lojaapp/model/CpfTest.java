package br.com.springboot.lojaapp.model;

import br.com.caelum.stella.validation.InvalidStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfTest {

    // Mesmos CPFs usados no data.sql — garante que a massa passa na validação da Stella no load.
    @ParameterizedTest
    @ValueSource(strings = {
            "11144477735", "52998224725", "39053344705",
            "04252011477", "71286819067", "30828009554"
    })
    void aceitaCpfValido(String cpf) {
        assertEquals(cpf, new Cpf(cpf).valor());
    }

    @Test
    void normalizaRemovendoMascara() {
        assertEquals("11144477735", new Cpf("111.444.777-35").valor());
    }

    @Test
    void aceitaValorNulo() {
        assertNull(new Cpf(null).valor());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678900", "11111111111", "111444777"})
    void rejeitaCpfInvalido(String cpf) {
        assertThrows(InvalidStateException.class, () -> new Cpf(cpf));
    }
}
