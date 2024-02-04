package teko.ch.zigbee.baseApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

public class jsonFile {

    public static void writeJsonToFile(JsonNode jsonNode, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonNode);
    }

    public static JsonNode readJsonFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(filePath));
    }
    public void updateProductBriValue(String filePath, String productName, int newBriValue) {
        try {
            JsonNode jsonData = readJsonFromFile(filePath);

            // Iterate through each entry in the JSON object
            jsonData.fields().forEachRemaining(entry -> {
                JsonNode product = entry.getValue();
                if (productName.equals(product.get("name").asText())) {
                    ((ObjectNode) product.get("state")).put("bri", newBriValue);
                }
            });

            writeJsonToFile(jsonData, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateProductColor(String filePath, String productName, double newX, double newY) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(filePath);

        // Read the JSON file into a Java object (or a Map/Node structure if more appropriate)
        ObjectNode root = (ObjectNode) mapper.readTree(jsonFile);

        // Assuming the JSON structure, navigate to the correct "Pult" product
        // This path might need to be adjusted based on the actual JSON structure
        ObjectNode pult = (ObjectNode) root.path("3").path("state");

        // Update the xy value
        double[] newXY = {newX, newY};
        pult.putArray("xy").add(newX).add(newY);

        // Write the modified JSON back to the file
        mapper.writeValue(jsonFile, root);
    }
    public static void updateProductOn(String filePath, String productName, boolean on) throws IOException {
        try {
            JsonNode jsonData = readJsonFromFile(filePath);

            // Iterate through each entry in the JSON object
            jsonData.fields().forEachRemaining(entry -> {
                JsonNode product = entry.getValue();
                if (productName.equals(product.get("name").asText())) {
                    ((ObjectNode) product.get("state")).put("on", on);
                }
            });

            writeJsonToFile(jsonData, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
