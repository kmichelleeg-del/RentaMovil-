import java.util.Locale;

public class Automovil extends Vehiculo {
    private int cantidadPasajeros;
    private boolean automatico;

    public Automovil(String placa, String marca, String modelo,
                     double tarifaDiaria, int cantidadPasajeros,
                     boolean automatico) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.automatico = automatico;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);

        double total = getTarifaDiaria() * dias;

        if (automatico) {
            total = total + (50 * dias);
        }

        return total;
    }

    @Override
    public String getCategoria() {
        return "Automovil";
    }

    @Override
    public String getCaracteristicasEspecificas() {
        String transmision = "manual";

        if (automatico) {
            transmision = "automatica";
        }

        return String.format(
                Locale.US,
                "%d pasajeros, transmision %s",
                cantidadPasajeros,
                transmision
        );
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isAutomatico() {
        return automatico;
    }
}