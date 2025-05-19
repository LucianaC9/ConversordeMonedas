package lad.com.alura.conversormoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

class RequestExample {
    public static void main(String[] args) {
        // Datos para construir la URL de la API
        String API_KEY = "f55eecd06b61f4aa323b4af6";
        String baseCurrency = "USD";
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;

        // Configuración de HttpRequest con personalización:
        HttpRequest request = HttpRequest.newBuilder()
                // Establece la URI a la que se realizará la solicitud
                .uri(URI.create(url))
                // Define un tiempo de espera de 10 segundos para la respuesta
                .timeout(Duration.ofSeconds(10))
                // Agrega una cabecera para indicar el formato de respuesta esperado
                .header("Accept", "application/json")
                // Se puede agregar más personalización (por ejemplo, otros headers o un body en caso de POST)
                .GET()   // Especifica que la solicitud es de tipo GET
                .build();

        // Se crea el HttpClient para enviar la solicitud
        HttpClient client = HttpClient.newHttpClient();
        try {
            // Se envía la solicitud y se obtiene la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Se muestra el código de estado HTTP y la respuesta en la consola
            System.out.println("Código de estado: " + response.statusCode());
            System.out.println("Respuesta JSON:");
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }
}
