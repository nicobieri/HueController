package teko.ch.zigbee.baseApi;



// Http://192.168.1.103/api/5kAfpCWnRQeUphawHE7yN2Fon4l4ZJjzqvVo788T

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class HueBridgeController {
    private String bridgeBaseUrl;
    private String apiKey;

    public HueBridgeController(String bridgeBaseUrl, String apiKey) {
        this.bridgeBaseUrl = bridgeBaseUrl;
        this.apiKey = apiKey;
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
    public void getLampState(int lampNumber) throws IOException {
        String apiUrl = bridgeBaseUrl + apiKey + "/lights/" + lampNumber;

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

            // Print the response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());

// To access a specific field, e.g., "name"
            JsonNode nameNode = rootNode.get("name");
            System.out.println("Lamp Name: " + nameNode.asText());

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}