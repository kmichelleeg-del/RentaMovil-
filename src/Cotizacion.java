import java.util.Locale;

public class Cotizacion {
    private Vehiculo vehiculo;
    private int dias;
    private double total;

    public Cotizacion(Vehiculo vehiculo, int dias) {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                    "El vehiculo es obligatorio.");
        }

        this.vehiculo = vehiculo;
        this.dias = dias;
        this.total = vehiculo.calcularCosto(dias);
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getDias() {
        return dias;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "%s%nDias: %d%nCosto total: Q%.2f",
                vehiculo,
                dias,
                total
        );
    }
}