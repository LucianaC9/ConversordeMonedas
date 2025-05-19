package lad.com.alura.conversormoneda;

import java.util.Scanner;

public class Conversor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        double tasaARS = 112.5, tasaBRL = 5.3, tasaCOP = 3770.0;

        System.out.println("Bienvenido/a al Conversor de Monedas");

        do {
            // Mostrar el menú de opciones
            System.out.println("\nSeleccione la conversión deseada:");
            System.out.println("1) Dólar => Peso argentino");
            System.out.println("2) Peso argentino => Dólar");
            System.out.println("3) Dólar => Real brasileño");
            System.out.println("4) Real brasileño => Dólar");
            System.out.println("5) Dólar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.print("Ingrese una opción válida: ");

            // Leer la opción del usuario
            opcion = scanner.nextInt();

            // Si la opción es válida, solicitar monto y calcular la conversión
            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese el monto a convertir: ");
                double monto = scanner.nextDouble();
                double resultado = 0;
                String monedaOrigen = "", monedaDestino = "";

                switch (opcion) {
                    case 1:
                        resultado = monto * tasaARS;
                        monedaOrigen = "USD";
                        monedaDestino = "ARS";
                        break;
                    case 2:
                        resultado = monto / tasaARS;
                        monedaOrigen = "ARS";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        resultado = monto * tasaBRL;
                        monedaOrigen = "USD";
                        monedaDestino = "BRL";
                        break;
                    case 4:
                        resultado = monto / tasaBRL;
                        monedaOrigen = "BRL";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        resultado = monto * tasaCOP;
                        monedaOrigen = "USD";
                        monedaDestino = "COP";
                        break;
                    case 6:
                        resultado = monto / tasaCOP;
                        monedaOrigen = "COP";
                        monedaDestino = "USD";
                        break;
                }

                // Mostrar el resultado
                System.out.printf("%.2f %s equivalen a %.2f %s%n", monto, monedaOrigen, resultado, monedaDestino);
            } else if (opcion != 7) {
                System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 7); // Repetir hasta que el usuario seleccione "Salir"

        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
        scanner.close();
    }
}
