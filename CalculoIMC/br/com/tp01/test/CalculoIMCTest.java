package br.com.tp01.test;

import br.com.tp01.CalculoIMC;
import br.com.tp01.IMCService;
import br.com.tp01.ValidadorDeDadosService;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.*;
import net.jqwik.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Property
    void testIMCSemprePositivo(@ForAll @DoubleRange(min = 30, max = 300, maxIncluded = false) double peso,
                           @ForAll @DoubleRange(min = 1.0, max = 2.5, maxIncluded = false) double altura) {
        double imc = CalculoIMC.calcularIMC(peso, altura);
        assertTrue(imc > 0, "IMC deve ser positivo");
    }

    @Property
    void testPesoExtremo(@ForAll("pesosExtremosAcima500") double peso,
                           @ForAll("alturaAceitavel") double altura) {
        if (peso >= 300) {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(peso, altura),
                    "Valores inválidos para peso.");
        }
    }

    @Property
    void testAlturasImprovaveis(@ForAll("pesoAceitavel")  double peso,
                                @ForAll("alturasImprovaveis") double altura) {
        if (altura <= 0.5 || altura >= 2.5) {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(peso, altura),
                    "Valores inválidos para altura.");
        }
    }

    @Property
    void testIMCComValoresAleatorios(@ForAll double peso, @ForAll double altura) {
        double imc = CalculoIMC.calcularIMC(peso, altura);
        assertThat(imc).isGreaterThanOrEqualTo(0);
    }

    @Property
    void testIMCComCasosEspecíficos() {
        double peso = 80;
        double altura = 1.76;
        double imc = CalculoIMC.calcularIMC(peso, altura);
        assertThat(imc).isBetween(10.0, 50.0);
    }

    @Test
    void testCalculoIMCComMock() {
        IMCService imcService = mock(IMCService.class);
        when(imcService.calcularIMC(80, 1.80)).thenReturn(24.69);

        double imc = imcService.calcularIMC(80, 1.80);
        assertThat(imc).isEqualTo(24.69);
    }

    @Test
    void testCalcularIMCComPesoInvalido() {
        ValidadorDeDadosService mockValidador = mock(ValidadorDeDadosService.class);
        when(mockValidador.validarPeso(80)).thenReturn(false);

        IMCService imcService = new IMCService(mockValidador);
        assertThrows(IllegalArgumentException.class, () -> {
            imcService.calcularIMC(80, 1.80);
        });

        verify(mockValidador).validarPeso(80);
    }

    @Provide
    Arbitrary<Double> pesosExtremosAcima500() {
        return Arbitraries.doubles().between(500, 1000);
    }

    @Provide
    Arbitrary<Double> pesoAceitavel() {
        return Arbitraries.doubles().between(30, 499);
    }

    @Provide
    Arbitrary<Double> alturaAceitavel() {
        return Arbitraries.doubles().between(1.0, 2.5);
    }

    @Provide
    Arbitrary<Double> alturasImprovaveis() {
        return Arbitraries.of(0.5, 3.0, 5.0);
    }
}
