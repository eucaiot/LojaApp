package br.com.eucaiot.lojaapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CpfTest {

    @Test
    void normalizaRemovendoMascara() {
        assertEquals("11144477735", new Cpf("111.444.777-35").valor());
    }

    @Test
    void mantemApenasDigitos() {
        assertEquals("11144477735", new Cpf(" 111 444 777-35 ").valor());
    }

    @Test
    void aceitaValorNulo() {
        assertNull(new Cpf(null).valor());
    }
}
