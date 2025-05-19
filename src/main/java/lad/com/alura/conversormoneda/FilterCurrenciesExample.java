package lad.com.alura.conversormoneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.List;

public class FilterCurrenciesExample {
    public static void main(String[] args) {
        // Simulación de una respuesta JSON de la API que incluye "conversion_rates"
        String jsonResponse = """
                {
                  "result": "success",
                  "base_code": "USD",
                  "conversion_rates": {
                    "USD": 1,
                    "ARS": 112.5,
                    "BOB": 6.96,
                    "BRL": 5.3,
                    "CLP": 866.5,
                    "COP": 3770.0,
                    "EUR": 0.85,
                    "JPY": 110.53
                  }
                }""";

        // Parseamos el JSON con JsonParser y convertimos a un JsonObject
        JsonElement jsonTree = JsonParser.parseString(jsonResponse);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            // Accedemos al objeto que contiene las tasas de conversión
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            // Definimos una lista con los Currency Codes que queremos filtrar.
            // En este ejemplo, elegimos ARS, COP y CLP.
            List<String> allowedCurrencies = Arrays.asList("ARS", "COP", "CLP");

            System.out.println("Tasas de conversión filtradas:");

            // Iteramos sobre la lista de monedas permitidas y mostramos su tasa si existe en el JSON
            for (String currencyCode : allowedCurrencies) {
                if (conversionRates.has(currencyCode)) {
                    double rate = conversionRates.get(currencyCode).getAsDouble();
                    System.out.println(currencyCode + " : " + rate);
                } else {
                    System.out.println(currencyCode + " no se encontró en la respuesta.");
                }
            }
        } else {
            System.out.println("El JSON proporcionado no es un objeto válido.");
        }
    }
}
