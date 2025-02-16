package br.com.tp01.test;

import br.com.tp01.CalculoIMC;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalculoIMCTest {

    @Test
    void testClassificacaoIMC() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(22.0));
        assertEquals("Magreza grave", CalculoIMC.classificarIMC(15.0));
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(27.0));
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(42.0));
    }

    @Test
    void testValidIMC() {
        assertEquals(22.85, CalculoIMC.calcularIMC(70, 1.75), 0.01);
    }

    @Test
    void testInvalidIMC() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(-10, 1.75));
        assertEquals("Valores inválidos.",exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(80, -1.75));
        assertEquals("Valores inválidos.",exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(0, -1.75));
        assertEquals("Valores inválidos.",exception.getMessage());
    }

    @Test
    void testLimitIMC() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(0, 1.75));
        assertEquals("Valores inválidos.",exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(80, 0));
        assertEquals("Valores inválidos.",exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(500, 3.5));
        assertEquals("Valores inválidos.",exception.getMessage());
    }
}
