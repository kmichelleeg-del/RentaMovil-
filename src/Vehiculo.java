import java.util.Locale;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    protected Vehiculo(String placa, String marca, String modelo,
                       double tarifaDiaria) {
        this.placa = validarTexto(placa, "La placa")
                .toUpperCase(Locale.ROOT);
        this.marca = validarTexto(marca, "La marca");
        this.modelo = validarTexto(modelo, "El modelo");

        if (!Double.isFinite(tarifaDiaria) || tarifaDiaria <= 0) {
            throw new IllegalArgumentException(
                    "La tarifa diaria debe ser mayor que cero.");
        }

        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;
    }

    private static String validarTexto(String valor, String nombre) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    nombre + " no puede estar vacia.");
        }

        return valor.trim();
    }

    protected static void validarDias(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias deben ser un entero positivo.");
        }
    }

    public abstract double calcularCosto(int dias);

    public abstract String getCategoria();

    public abstract String getCaracteristicasEspecificas();

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    void marcarComoAlquilado() {
        if (!disponible) {
            throw new IllegalStateException(
                    "El vehiculo ya se encuentra alquilado.");
        }

        disponible = false;
    }

    void marcarComoDisponible() {
        if (disponible) {
            throw new IllegalStateException(
                    "El vehiculo ya se encuentra disponible.");
        }

        disponible = true;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "%s | %s | %s %s | Q%.2f por dia | %s | %s",
                placa,
                getCategoria(),
                marca,
                modelo,
                tarifaDiaria,
                disponible ? "Disponible" : "Alquilado",
                getCaracteristicasEspecificas()
        );
    }
}