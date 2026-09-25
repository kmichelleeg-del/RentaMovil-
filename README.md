# RentaMovil

Este es el programa del Ejercicio 4 de Programación Orientada a Objetos. Es una
aplicación de consola para llevar el control de los vehículos de una empresa de
alquiler.

## Requisitos

- Java JDK 17 o una versión más reciente.

## Compilar

Desde la carpeta `RentaMovil`:

```bash
javac -d out src/*.java
```

## Ejecutar la aplicación

```bash
java -cp out Main
```

La aplicación inicia con dos vehículos de cada categoría.

## Ejecutar las pruebas

```bash
java -cp out PruebasRentaMovil
```

Las pruebas revisan los cálculos de cada tipo de vehículo y también comprueban
que una cotización, una operación rechazada o una devolución no sumen ingresos.

## Estructura

- `Vehiculo`: guarda los datos que tienen todos los vehículos.
- `Automovil`, `Motocicleta` y `CamionetaCarga`: calculan su costo de alquiler.
- `Cotizacion`: guarda la información de una consulta de precio.
- `RentaMovil`: administra la flota, los alquileres y los ingresos.
- `Main`: contiene el menú y lee los datos ingresados por el usuario.
- `PruebasRentaMovil`: ejecuta las pruebas sin necesitar otras librerías.
