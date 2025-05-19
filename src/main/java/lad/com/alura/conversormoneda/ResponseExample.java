package lad.com.alura.conversormoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ResponseExample {
    public static void main(String[] args) {
        // Datos necesarios para construir la URL de la API
        String API_KEY = "f55eecd06b61f4aa323b4af6";
        String baseCurrency = "USD";
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;

        // Configurando HttpClient y HttpRequest
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // Enviamos la solicitud y obtenemos la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 1. Acceder y mostrar el código de estado HTTP
            int statusCode = response.statusCode();
            System.out.println("Código de Estado HTTP: " + statusCode);

            // 2. Acceder y mostrar las cabeceras de la respuesta
            HttpHeaders headers = response.headers();
            System.out.println("\nEncabezados de la respuesta:");
            headers.map().forEach((key, values) -> System.out.println(key + ": " + values));

            // 3. Obtener y mostrar el cuerpo de la respuesta
            String responseBody = response.body();
            System.out.println("\nCuerpo de la respuesta (JSON):");
            System.out.println(responseBody);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
