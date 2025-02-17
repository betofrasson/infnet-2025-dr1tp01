package br.com.tp01;

public class IMCService {
    private ValidadorDeDadosService validadorDeDadosService;

    public IMCService(ValidadorDeDadosService validadorDeDadosService) {
        this.validadorDeDadosService = validadorDeDadosService;
    }

    public double calcularIMC(double peso, double altura) {
        if (!validadorDeDadosService.validarPeso(peso)) {
            throw new IllegalArgumentException("Peso inválido");
        }
        if (!validadorDeDadosService.validarAltura(altura)) {
            throw new IllegalArgumentException("Altura inválida");
        }

        return peso / (altura * altura);
    }
}
