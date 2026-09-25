import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private RentaMovil empresa;

    public Main() {
        scanner = new Scanner(System.in);
        empresa = new RentaMovil();
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Main programa = new Main();
        programa.cargarDatosIniciales();
        programa.ejecutarMenu();
    }

    private void cargarDatosIniciales() {
        empresa.registrarVehiculo(
                new Automovil(
                        "A001ABC", "Toyota", "Corolla",
                        300, 5, true
                )
        );

        empresa.registrarVehiculo(
                new Automovil(
                        "A002ABC", "Honda", "Civic",
                        250, 5, false
                )
        );

        empresa.registrarVehiculo(
                new Motocicleta(
                        "M001ABC", "Yamaha", "FZ",
                        125, 150
                )
        );

        empresa.registrarVehiculo(
                new Motocicleta(
                        "M002ABC", "Kawasaki", "Ninja",
                        175, 300
                )
        );

        empresa.registrarVehiculo(
                new CamionetaCarga(
                        "C001ABC", "Toyota", "Hilux",
                        200, 1.5
                )
        );

        empresa.registrarVehiculo(
                new CamionetaCarga(
                        "C002ABC", "Ford", "Ranger",
                        275, 3.0
                )
        );
    }

    private void ejecutarMenu() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarVehiculo();
                        break;

                    case 2:
                        consultarFlota();
                        break;

                    case 3:
                        cotizar();
                        break;

                    case 4:
                        confirmarAlquiler();
                        break;

                    case 5:
                        registrarDevolucion();
                        break;

                    case 6:
                        mostrarReporte();
                        break;

                    case 0:
                        System.out.println(
                                "Gracias por usar RentaMovil.");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Operacion rechazada: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println(
                        "Operacion rechazada: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("RentaMovil");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolucion");
        System.out.println("6. Ver reporte general");
        System.out.println("0. Salir");
    }

    private void registrarVehiculo() {
        System.out.println();
        System.out.println("1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");

        int tipo = leerEntero("Tipo de vehiculo: ");

        if (tipo < 1 || tipo > 3) {
            throw new IllegalArgumentException(
                    "El tipo de vehiculo no es valido.");
        }

        String placa = leerTexto("Placa: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        double tarifa = leerDoublePositivo(
                "Tarifa diaria: Q");

        Vehiculo nuevo;

        if (tipo == 1) {
            int pasajeros = leerEnteroPositivo(
                    "Cantidad de pasajeros: ");

            boolean automatico = leerSiNo(
                    "Transmision automatica?: ");

            nuevo = new Automovil(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    pasajeros,
                    automatico
            );

        } else if (tipo == 2) {
            int cilindraje = leerEnteroPositivo(
                    "Cilindraje en cc: ");

            nuevo = new Motocicleta(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    cilindraje
            );

        } else {
            double capacidad = leerDoublePositivo(
                    "Capacidad maxima en toneladas: ");

            nuevo = new CamionetaCarga(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    capacidad
            );
        }

        empresa.registrarVehiculo(nuevo);

        System.out.println(
                "Vehiculo registrado correctamente.");
    }

    private void consultarFlota() {
        System.out.println();
        System.out.println("Flota registrada");

        ArrayList<Vehiculo> vehiculos =
                empresa.getVehiculos();

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    private void cotizar() {
        String placa = leerTexto("Placa: ");
        int dias = leerEnteroPositivo(
                "Dias de alquiler: ");

        Cotizacion cotizacion =
                empresa.cotizar(placa, dias);

        System.out.println();
        System.out.println("Cotizacion");
        System.out.println(cotizacion);
        System.out.println(
                "Esta consulta no reserva el vehiculo "
                        + "ni registra ingresos.");
    }

    private void confirmarAlquiler() {
        String placa = leerTexto("Placa: ");
        int dias = leerEnteroPositivo(
                "Dias de alquiler: ");

        Cotizacion cotizacion =
                empresa.cotizar(placa, dias);

        if (!cotizacion.getVehiculo().isDisponible()) {
            throw new IllegalStateException(
                    "El vehiculo esta ocupado "
                            + "y no puede alquilarse.");
        }

        System.out.println(cotizacion);

        boolean confirmar = leerSiNo(
                "Confirmar alquiler y cobro?: ");

        if (confirmar) {
            double total =
                    empresa.confirmarAlquiler(placa, dias);

            System.out.printf(
                    Locale.US,
                    "Alquiler confirmado. Cobro: ",
                    total
            );
        } else {
            System.out.println(
                    "Operacion cancelada. "
                            + "No se modifico la informacion.");
        }
    }

    private void registrarDevolucion() {
        String placa = leerTexto("Placa: ");

        empresa.registrarDevolucion(placa);

        System.out.println(
                "Devolucion registrada. "
                        + "El vehiculo vuelve a estar disponible.");
    }

    private void mostrarReporte() {
        int total = empresa.getCantidadVehiculos();
        int disponibles = empresa.contarDisponibles();
        int alquilados = total - disponibles;

        System.out.println();
        System.out.println("Reporte general");
        System.out.println(
                "Vehiculos registrados: " + total);
        System.out.println(
                "Disponibles: " + disponibles);
        System.out.println(
                "Alquilados: " + alquilados);

        mostrarCategoria("Automovil");
        mostrarCategoria("Motocicleta");
        mostrarCategoria("Camioneta de carga");

        System.out.printf(
                Locale.US,
                "Ingresos acumulados:",
                empresa.getIngresosAcumulados()
        );
    }

    private void mostrarCategoria(String categoria) {
        int total =
                empresa.contarCategoria(categoria);

        int disponibles =
                empresa.contarDisponiblesCategoria(categoria);

        int alquilados = total - disponibles;

        System.out.println(
                categoria
                        + ": total " + total
                        + ", disponibles " + disponibles
                        + ", alquilados " + alquilados
        );
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                String entrada = leerTexto(mensaje);
                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. "
                                + "Escriba un numero entero.");
            }
        }
    }

    private int leerEnteroPositivo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);

            if (valor > 0) {
                return valor;
            }

            System.out.println(
                    "El valor debe ser mayor que cero.");
        }
    }

    private double leerDoublePositivo(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);
            entrada = entrada.replace(',', '.');

            try {
                double valor =
                        Double.parseDouble(entrada);

                if (Double.isFinite(valor)
                        && valor > 0) {
                    return valor;
                }

                System.out.println(
                        "El valor debe ser mayor que cero.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. Escriba un numero.");
            }
        }
    }

    private boolean leerSiNo(String mensaje) {
        while (true) {
            String respuesta =
                    leerTexto(mensaje).toLowerCase(
                            Locale.ROOT);

            if (respuesta.equals("s")
                    || respuesta.equals("si")) {
                return true;
            }

            if (respuesta.equals("n")
                    || respuesta.equals("no")) {
                return false;
            }

            System.out.println("Responda s o n.");
        }
    }
}