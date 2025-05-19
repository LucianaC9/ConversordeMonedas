package lad.com.alura.conversormoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    // Clave API y parte fija de la URL
    private static final String API_KEY = "f55eecd06b61f4aa323b4af6";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    /**
     * Realiza una solicitud GET a la API de ExchangeRate-API para obtener la tasa de cambio.
     *
     * @param monedaBase La moneda en la que se solicita la tasa (por ejemplo, "USD").
     * @return HttpResponse<String> que contiene el cuerpo de la respuesta en formato JSON.
     */
    public static HttpResponse<String> obtenerRespuesta(String monedaBase) throws IOException, InterruptedException {
        // Construyendo la URL completa: por ejemplo, https://v6.exchangerate-api.com/v6/f55eecd06b61f4aa323b4af6/latest/USD
        String url = BASE_URL + monedaBase;

        // Crear la instancia de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Construir la solicitud HTTP con la URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET() // se especifica el método GET
                .build();

        // Enviar la solicitud y recibir la respuesta de forma síncrona

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Método principal para probar la conexión vía HttpClient
    public static void main(String[] args) {
        try {
            // Por ejemplo, solicitamos tasas con base en "USD"
            HttpResponse<String> respuesta = obtenerRespuesta("USD");
            System.out.println("Código de estado HTTP: " + respuesta.statusCode());
            System.out.println("Respuesta JSON:\n" + respuesta.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
