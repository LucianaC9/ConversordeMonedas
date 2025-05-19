import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class TestGsonManual {
    public static void main(String[] args) {
        // JSON de ejemplo: contiene un campo "conversion_rate" con valor 1.23
        String json = "{\"conversion_rate\": 1.23}";

        // Parsear el String JSON en un objeto JsonElement
        JsonElement elemento = JsonParser.parseString(json);

        // Convertir el JsonElement en un JsonObject para acceder a sus claves
        JsonObject objetoJson = elemento.getAsJsonObject();

        // Extraer el valor de "conversion_rate" y convertirlo a double
        double conversionRate = objetoJson.get("conversion_rate").getAsDouble();

        // Imprimir el valor de "conversion_rate"
        System.out.println("Conversion rate: " + conversionRate);
    }
}
