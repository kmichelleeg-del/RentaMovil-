import java.util.Locale;

public class PruebasRentaMovil {
    private static int pruebasCorrectas = 0;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        RentaMovil empresa = crearEmpresa();

        revisarCosto(
                "Auto automatico por 3 dias",
                1050,
                empresa.cotizar(
                        "A001ABC", 3).getTotal()
        );

        revisarCosto(
                "Auto manual por 3 dias",
                750,
                empresa.cotizar(
                        "A002ABC", 3).getTotal()
        );

        revisarCosto(
                "Moto de 150 cc por 3 dias",
                375,
                empresa.cotizar(
                        "M001ABC", 3).getTotal()
        );

        revisarCosto(
                "Moto de 300 cc por 3 dias",
                600,
                empresa.cotizar(
                        "M002ABC", 3).getTotal()
        );

        revisarCosto(
                "Camioneta de 1.5 toneladas por 3 dias",
                1050,
                empresa.cotizar(
                        "C001ABC", 3).getTotal()
        );

        revisarCosto(
                "La cotizacion no suma ingresos",
                0,
                empresa.getIngresosAcumulados()
        );

        double cobro =
                empresa.confirmarAlquiler(
                        "A001ABC", 2);

        revisarCosto(
                "Alquiler confirmado",
                700,
                cobro
        );

        revisarCosto(
                "Ingresos despues del alquiler",
                700,
                empresa.getIngresosAcumulados()
        );

        revisarCondicion(
                "El vehiculo queda ocupado",
                !empresa.buscarPorPlaca(
                        "A001ABC").isDisponible()
        );

        probarAlquilerOcupado(empresa);

        revisarCosto(
                "El intento fallido no suma ingresos",
                700,
                empresa.getIngresosAcumulados()
        );

        empresa.registrarDevolucion("A001ABC");

        revisarCondicion(
                "La devolucion deja el vehiculo disponible",
                empresa.buscarPorPlaca(
                        "A001ABC").isDisponible()
        );

        revisarCosto(
                "La devolucion no cambia los ingresos",
                700,
                empresa.getIngresosAcumulados()
        );

        probarDevolucionInvalida(empresa);
        probarPlacaInexistente(empresa);
        probarDiasInvalidos(empresa);
        probarPlacaRepetida(empresa);

        System.out.println();
        System.out.println(
                "Resultado: "
                        + pruebasCorrectas
                        + " pruebas superadas.");
    }

    private static RentaMovil crearEmpresa() {
        RentaMovil empresa = new RentaMovil();

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

        return empresa;
    }

    private static void probarAlquilerOcupado(
            RentaMovil empresa) {
        try {
            empresa.confirmarAlquiler(
                    "A001ABC", 1);

            fallar(
                    "Se permitio alquilar "
                            + "un vehiculo ocupado");

        } catch (IllegalStateException e) {
            pruebaCorrecta(
                    "Se rechazo el alquiler "
                            + "de un vehiculo ocupado");
        }
    }

    private static void probarDevolucionInvalida(
            RentaMovil empresa) {
        try {
            empresa.registrarDevolucion("A001ABC");

            fallar(
                    "Se permitio devolver "
                            + "un vehiculo disponible");

        } catch (IllegalStateException e) {
            pruebaCorrecta(
                    "Se rechazo la devolucion "
                            + "de un vehiculo disponible");
        }
    }

    private static void probarPlacaInexistente(
            RentaMovil empresa) {
        try {
            empresa.cotizar("NO-EXISTE", 1);

            fallar(
                    "Se acepto una placa inexistente");

        } catch (IllegalArgumentException e) {
            pruebaCorrecta(
                    "Se rechazo una placa inexistente");
        }
    }

    private static void probarDiasInvalidos(
            RentaMovil empresa) {
        try {
            empresa.cotizar("M001ABC", 0);

            fallar("Se aceptaron cero dias");

        } catch (IllegalArgumentException e) {
            pruebaCorrecta(
                    "Se rechazaron los dias invalidos");
        }
    }

    private static void probarPlacaRepetida(
            RentaMovil empresa) {
        try {
            Motocicleta moto = new Motocicleta(
                    "m001abc",
                    "Honda",
                    "CB",
                    100,
                    125
            );

            empresa.registrarVehiculo(moto);

            fallar("Se acepto una placa repetida");

        } catch (IllegalArgumentException e) {
            pruebaCorrecta(
                    "Se rechazo una placa repetida");
        }
    }

    private static void revisarCosto(
            String nombre,
            double esperado,
            double obtenido) {

        boolean correcto =
                Math.abs(esperado - obtenido) < 0.001;

        if (correcto) {
            pruebaCorrecta(
                    nombre
                            + " - esperado Q"
                            + String.format(
                                    "%.2f", esperado)
                            + ", obtenido Q"
                            + String.format(
                                    "%.2f", obtenido)
            );
        } else {
            fallar(
                    nombre
                            + " - esperado Q"
                            + esperado
                            + ", obtenido Q"
                            + obtenido
            );
        }
    }

    private static void revisarCondicion(
            String nombre,
            boolean condicion) {

        if (condicion) {
            pruebaCorrecta(nombre);
        } else {
            fallar(nombre);
        }
    }

    private static void pruebaCorrecta(
            String mensaje) {
        pruebasCorrectas++;
        System.out.println("OK - " + mensaje);
    }

    private static void fallar(String mensaje) {
        throw new AssertionError(
                "FALLO - " + mensaje);
    }
}