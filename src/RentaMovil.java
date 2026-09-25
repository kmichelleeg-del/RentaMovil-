import java.util.ArrayList;
import java.util.Locale;

public class RentaMovil {
    private ArrayList<Vehiculo> vehiculos;
    private double ingresosAcumulados;

    public RentaMovil() {
        vehiculos = new ArrayList<Vehiculo>();
        ingresosAcumulados = 0;
    }

    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                    "El vehiculo es obligatorio.");
        }

        if (buscarSinError(vehiculo.getPlaca()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un vehiculo con la placa "
                            + vehiculo.getPlaca() + ".");
        }

        vehiculos.add(vehiculo);
    }

    public Vehiculo buscarPorPlaca(String placa) {
        validarPlaca(placa);

        Vehiculo encontrado = buscarSinError(placa);

        if (encontrado == null) {
            throw new IllegalArgumentException(
                    "No existe un vehiculo con esa placa.");
        }

        return encontrado;
    }

    private Vehiculo buscarSinError(String placa) {
        if (placa == null) {
            return null;
        }

        String placaBuscada = placa.trim().toUpperCase(Locale.ROOT);

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equals(placaBuscada)) {
                return vehiculo;
            }
        }

        return null;
    }

    public Cotizacion cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        return new Cotizacion(vehiculo, dias);
    }

    public double confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarPorPlaca(placa);

        if (!vehiculo.isDisponible()) {
            throw new IllegalStateException(
                    "El vehiculo esta ocupado y no puede alquilarse.");
        }

        double total = vehiculo.calcularCosto(dias);

        vehiculo.marcarComoAlquilado();
        ingresosAcumulados = ingresosAcumulados + total;

        return total;
    }

    public void registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarPorPlaca(placa);

        if (vehiculo.isDisponible()) {
            throw new IllegalStateException(
                    "No se puede devolver un vehiculo "
                            + "que ya esta disponible.");
        }

        vehiculo.marcarComoDisponible();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return new ArrayList<Vehiculo>(vehiculos);
    }

    public int contarDisponibles() {
        int cantidad = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.isDisponible()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarCategoria(String categoria) {
        int cantidad = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getCategoria().equals(categoria)) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarDisponiblesCategoria(String categoria) {
        int cantidad = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getCategoria().equals(categoria)
                    && vehiculo.isDisponible()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int getCantidadVehiculos() {
        return vehiculos.size();
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }

    private void validarPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La placa no puede estar vacia.");
        }
    }
}