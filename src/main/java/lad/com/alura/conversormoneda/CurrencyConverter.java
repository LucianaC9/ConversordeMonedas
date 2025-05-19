package lad.com.alura.conversormoneda;

import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        // Tasas de conversión simuladas: se usan como si fueran los valores obtenidos de la API.
        // Estas tasas se interpretan como "1 USD equivale a X unidades de la moneda".
        double rateARS = 112.5;  // Peso argentino
        double rateBOB = 6.96;   // Boliviano
        double rateBRL = 5.3;    // Real brasileño
        double rateCLP = 866.5;  // Peso chileno
        double rateCOP = 3770.0; // Peso colombiano

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al conversor de monedas.");
        System.out.print("Ingrese el monto a convertir: ");
        double amount = scanner.nextDouble();

        System.out.print("Ingrese la moneda de origen (USD, ARS, BOB, BRL, CLP, COP): ");
        String origen = scanner.next().toUpperCase();

        System.out.print("Ingrese la moneda de destino (USD, ARS, BOB, BRL, CLP, COP): ");
        String destino = scanner.next().toUpperCase();

        // Obtenemos la tasa de conversión para cada moneda.
        double rateOrigen = getConversionRate(origen, rateARS, rateBOB, rateBRL, rateCLP, rateCOP);
        double rateDestino = getConversionRate(destino, rateARS, rateBOB, rateBRL, rateCLP, rateCOP);

        // Validamos que ambos códigos de moneda sean válidos.
        if (rateOrigen == 0 || rateDestino == 0) {
            System.out.println("Error: uno de los códigos de moneda ingresados no es válido.");
            scanner.close();
            return;
        }

        /*
         * Para realizar la conversión, utilizamos la siguiente lógica:
         * - Si el monto está en una moneda que no es USD (la moneda base de las tasas), lo convertimos primero a USD.
         * - Luego, de USD convertimos a la moneda destino.
         *
         * Fórmula:
         *   montoEnUSD = (origen es USD) ? monto : monto / tasaOrigen
         *   montoConvertido = (destino es USD) ? montoEnUSD : montoEnUSD * tasaDestino
         */

        double amountInUSD = (origen.equals("USD")) ? amount : amount / rateOrigen;
        double convertedAmount = (destino.equals("USD")) ? amountInUSD : amountInUSD * rateDestino;

        // Se muestra el resultado final
        System.out.printf("%.2f %s equivalen a %.2f %s%n",
                amount, origen, convertedAmount, destino);

        scanner.close();
    }

    /**
     * Método auxiliar para obtener la tasa de conversión de USD a la moneda especificada.
     * Las tasas se deben haber obtenido anteriormente desde la API.
     *
     * @param currency Código de la moneda (por ejemplo, "ARS", "BOB", etc.)
     * @param rateARS  Tasa de conversión de USD a ARS.
     * @param rateBOB  Tasa de conversión de USD a BOB.
     * @param rateBRL  Tasa de conversión de USD a BRL.
     * @param rateCLP  Tasa de conversión de USD a CLP.
     * @param rateCOP  Tasa de conversión de USD a COP.
     * @return La tasa de conversión o 0 si el código no es reconocido.
     */
    public static double getConversionRate(String currency, double rateARS, double rateBOB,
                                           double rateBRL, double rateCLP, double rateCOP) {
        return switch (currency) {
            case "USD" -> 1;
            case "ARS" -> rateARS;
            case "BOB" -> rateBOB;
            case "BRL" -> rateBRL;
            case "CLP" -> rateCLP;
            case "COP" -> rateCOP;
            default -> 0; // Código no reconocido
        };
    }
}

