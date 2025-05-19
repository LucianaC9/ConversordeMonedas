package lad.com.alura.conversormoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ConversorInteractivo {
    private static final Map<String, Double> tasas = new LinkedHashMap<>();
    private static final List<String> historial = new ArrayList<>();
    private static final List<String> monedasImportantes = Arrays.asList(
            "USD", "EUR", "GBP", "JPY", "CNY", "CHF", "CAD", "AUD", "MXN", "BRL", "INR"
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Obtener tasas dinámicamente desde la API (base USD)
        if (!obtenerTasasDesdeAPI()) {
            System.out.println("Error al obtener tasas de la API.");
            return;
        }

        System.out.println("Bienvenido/a al Conversor de Monedas");

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Evita problemas con el scanner

            switch (opcion) {
                case 1:
                    convertirMoneda(scanner);
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    mostrarMonedasDisponibles();
                    break;
                case 4:
                    System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    // Muestra el menú de opciones
    private static void mostrarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1) Convertir moneda");
        System.out.println("2) Mostrar historial de conversiones");
        System.out.println("3) Ver todas las monedas disponibles");
        System.out.println("4) Salir");
        System.out.print("Ingrese una opción válida: ");
    }

    // Obtener tasas de conversión desde la API de ExchangeRate-API
    private static boolean obtenerTasasDesdeAPI() {
        String apiKey = "f55eecd06b61f4aa323b4af6";  // Sustituye con tu API key
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + "USD";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

            // Convertir JsonObject a Map<String, Double>
            tasas.clear();
            for (Map.Entry<String, JsonElement> entry : conversionRates.entrySet()) {
                if (monedasImportantes.contains(entry.getKey())) {
                    tasas.put(entry.getKey(), entry.getValue().getAsJsonPrimitive().getAsDouble());
                }
            }
            return true;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al obtener tasas: " + e.getMessage());
            return false;
        }
    }

    // Convertir moneda usando tasas obtenidas dinámicamente
    private static void convertirMoneda(Scanner scanner) {
        System.out.println("Monedas disponibles: " + tasas.keySet());

        System.out.print("Ingrese la moneda de origen: ");
        String origen = scanner.next().toUpperCase();

        System.out.print("Ingrese la moneda de destino: ");
        String destino = scanner.next().toUpperCase();

        if (!tasas.containsKey(origen) || !tasas.containsKey(destino)) {
            System.out.println("Error: código de moneda inválido.");
            return;
        }

        System.out.print("Ingrese el monto a convertir: ");
        double monto = scanner.nextDouble();

        double montoEnUSD = (origen.equals("USD")) ? monto : monto / tasas.get(origen);
        double montoConvertido = (destino.equals("USD")) ? montoEnUSD : montoEnUSD * tasas.get(destino);

        // Guardar en el historial con marca de tiempo
        String registro = generarRegistro(monto, origen, montoConvertido, destino);
        historial.add(registro);

        System.out.printf("%.2f %s equivalen a %.2f %s%n", monto, origen, montoConvertido, destino);
    }

    // Mostrar todas las monedas disponibles
    private static void mostrarMonedasDisponibles() {
        System.out.println("\nMonedas disponibles para conversión:");
        tasas.keySet().forEach(System.out::println);
    }

    // Mostrar historial de conversiones
    private static void mostrarHistorial() {
        System.out.println("\nHistorial de conversiones:");
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            historial.forEach(System.out::println);
        }
    }

    // Método para generar el registro con marca de tiempo
    private static String generarRegistro(double monto, String origen, double resultado, String destino) {
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %.2f %s -> %.2f %s", fechaHora.format(formatter), monto, origen, resultado, destino);
    }
}





