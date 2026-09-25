import java.util.Locale;

public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo,
                       double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException(
                    "El cilindraje debe ser mayor que cero.");
        }

        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);

        double total = getTarifaDiaria() * dias;

        if (cilindraje > 250) {
            total = total + 75;
        }

        return total;
    }

    @Override
    public String getCategoria() {
        return "Motocicleta";
    }

    @Override
    public String getCaracteristicasEspecificas() {
        return String.format(Locale.US, "%d cc", cilindraje);
    }

    public int getCilindraje() {
        return cilindraje;
    }
}