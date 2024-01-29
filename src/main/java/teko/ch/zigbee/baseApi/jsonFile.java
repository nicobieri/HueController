package teko.ch.zigbee.baseApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

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
}
