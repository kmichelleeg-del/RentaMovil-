import java.util.Locale;

public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo,
                          double tarifaDiaria,
                          double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);

        if (!Double.isFinite(capacidadToneladas)
                || capacidadToneladas <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad de carga debe ser mayor que cero.");
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);

        double tarifaBase = getTarifaDiaria() * dias;
        double recargoCarga = 100 * capacidadToneladas * dias;

        return tarifaBase + recargoCarga;
    }

    @Override
    public String getCategoria() {
        return "Camioneta de carga";
    }

    @Override
    public String getCaracteristicasEspecificas() {
        return String.format(
                Locale.US,
                "capacidad maxima de %.2f toneladas",
                capacidadToneladas
        );
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }
}