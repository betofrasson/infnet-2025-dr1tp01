package br.com.tp01;

public class ValidadorDeDadosService {
    public boolean validarPeso(double peso) {
        return peso > 0 && peso < 300;
    }

    public boolean validarAltura(double altura) {
        return altura > 0.5 && altura < 2.5;
    }
}
