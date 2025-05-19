package lad.com.alura.conversormoneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonAnalysisExample {
    public static void main(String[] args) {
        // Simulación de una respuesta JSON obtenida de la API (puedes obtener una real usando Postman)
        String jsonResponse = """
                {
                  "result": "success",
                  "documentation": "https://www.exchangerate-api.com/docs",
                  "terms_of_use": "https://www.exchangerate-api.com/terms",
                  "time_last_update_unix": 1625164800,
                  "time_last_update_utc": "Wed, 02 Jul 2025 00:00:00 GMT",
                  "base_code": "USD",
                  "conversion_rates": {
                    "USD": 1,
                    "EUR": 0.85,
                    "JPY": 110.53
                  }
                }""";

        // Parseamos el JSON con Gson usando JsonParser
        JsonElement jsonTree = JsonParser.parseString(jsonResponse);

        // Comprobamos que el elemento parseado es un objeto JSON
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            // Accedemos a la propiedad "result" (por ejemplo, "success")
            String result = jsonObject.get("result").getAsString();
            System.out.println("Result: " + result);

            // Accedemos a la propiedad "base_code"
            String baseCode = jsonObject.get("base_code").getAsString();
            System.out.println("Base Code: " + baseCode);

            // Obtenemos el objeto "conversion_rates" que contiene las tasas de conversión
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            // Ejemplo: Extraemos la tasa de conversión para EUR
            double eurRate = conversionRates.get("EUR").getAsDouble();
            System.out.println("Tasa de conversión de EUR: " + eurRate);

            // Listamos todas las tasas de conversión disponibles
            System.out.println("\nTodas las tasas de conversión:");
            conversionRates.entrySet().forEach(entry -> {
                String currency = entry.getKey();
                double rate = entry.getValue().getAsDouble();
                System.out.println(currency + ": " + rate);
            });
        } else {
            System.out.println("La respuesta no es un objeto JSON válido.");
        }
    }
}

