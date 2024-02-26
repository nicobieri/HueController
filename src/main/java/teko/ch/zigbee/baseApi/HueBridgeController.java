package teko.ch.zigbee.baseApi;



// Http://192.168.1.103/api/5kAfpCWnRQeUphawHE7yN2Fon4l4ZJjzqvVo788T

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;


public class HueBridgeController {
    private String bridgeBaseUrl;
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HueBridgeController(String bridgeBaseUrl, String apiKey) {
        this.bridgeBaseUrl = bridgeBaseUrl;
        this.apiKey = apiKey;
    }

    public void setAllLamps() throws IOException {
        System.out.println(getAllLamps());
        Path path = Paths.get("lights.json");
        String yourJsonString = Files.readString(path);
        Map<String, LightState> allLightsConfig = objectMapper.readValue(yourJsonString, new TypeReference<Map<String, LightState>>() {});

        // Iterate over each light in the configuration
        for (Map.Entry<String, LightState> entry : allLightsConfig.entrySet()) {
            String lightId = entry.getKey();
            LightState lightConfig = entry.getValue();

            // Create a new JSON object with only the "on," "bri," and "xy" properties
            Map<String, Object> filteredState = new HashMap<>();
            filteredState.put("on", lightConfig.getState().isOn());
            filteredState.put("bri", lightConfig.getState().getBri());
            filteredState.put("xy", lightConfig.getState().getXy());

            // Convert the filtered state object to JSON
            String stateJson = objectMapper.writeValueAsString(filteredState);

            // Construct the URL for the light
            String apiUrl = bridgeBaseUrl + apiKey + "/lights/" + lightId + "/state";

            // Send the PUT request to update the light's state
            System.out.println(apiUrl);
            System.out.println(stateJson);
            sendPutRequest(apiUrl, stateJson);
        }
    }



    private void sendPutRequest(String apiUrl, String stateJson) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = stateJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        connection.disconnect();
    }


    public void setLampState(int lampNumber, String on, String color) throws IOException {
        String apiUrl = bridgeBaseUrl + apiKey + "/lights/" + lampNumber + "/state";
        String json = "{\"" + on + "\":" + color + "}";


        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
            System.out.println(json);
            os.write(json);
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public void getLampState(int lampNumber) {
        HttpURLConnection connection = null;
        try {
            String apiUrl = bridgeBaseUrl + apiKey + "/lights/" + lampNumber;
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error No Connection to the Hue Bridge!");
                return;
            }

            // Read the response
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Process the response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());

            // Check if the response is empty or nameNode is null
            if (response.length() == 0 || !rootNode.has("name")) {
                System.out.println("Error No Connection to the Hue Bridge!");
                return;
            }

            // Print the lamp name
            JsonNode nameNode = rootNode.get("name");
            System.out.println("Lamp Name: " + nameNode.asText());

        } catch (IOException e) {
            // Handle IOException by printing an error message
            System.out.println("Error No Connection to the Hue Bridge!");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }



    public JsonNode getAllLamps() throws IOException {
        String apiUrl = bridgeBaseUrl + apiKey + "/lights/";

        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Read the response
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());

            if (rootNode != null) {
                return rootNode;
            } else {
                throw new IOException("Error: Response is null or invalid");
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}