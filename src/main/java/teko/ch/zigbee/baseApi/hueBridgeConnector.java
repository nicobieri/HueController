package teko.ch.zigbee.baseApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class hueBridgeConnector {
    public static String findIPFromMAC(String macAddress) {
        String ipAddress = null;
        try {
            // Run the command
            Process p = Runtime.getRuntime().exec("arp -a");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Read the output
            String line;
            while ((line = input.readLine()) != null) {
                line = line.trim();
                if (line.contains(macAddress)) {
                    String[] parts = line.split("\\s+");
                    System.out.println(parts.length);
                    if (parts.length >= 3) {
                        ipAddress = parts[0];
                        System.out.println(ipAddress);
                        return ipAddress;
                    }
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public static String getMyIP(String mac) {
        String ip = findIPFromMAC(mac);
        if (ip != null) {
            System.out.println("IP Address found: " + ip);
            return ip;
        } else {
            System.out.println("IP Address not found for MAC: " + mac);

        List<String> ipAddresses = new ArrayList<>();
        try {
            URL url = new URL("https://discovery.meethue.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ipAddress = jsonObject.getString("internalipaddress");
                ipAddresses.add(ipAddress);
            }

        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("no IP response");
        }
        String ipAddress = ipAddresses.toString();
        if (ipAddress.startsWith("[") && ipAddress.endsWith("]")) {
            ipAddress = ipAddress.substring(1, ipAddress.length() - 1);
        }
        return ipAddress;
    }
    }

    public String getKey(String macAddress) throws IOException {
        String bridgeIPs = getMyIP(macAddress);
        String bridgeKey = "";
            String apiUrl = "http://" + bridgeIPs + "/api";
            HttpURLConnection connection = null;
            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true); // This is needed for POST and PUT requests
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                // Create the JSON object to send
                String jsonInputString = "{\"devicetype\":\"my_hue_app#HueApp\"}";

                // Write JSON to the request body
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (ArithmeticException e) {
                   System.out.println("connetion nicht gefunden");
                }
                System.out.println("hello");
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                // Read the response
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    if(response.toString().contains("link button not pressed")){
                        return response.toString();
                    }

                    // Parse the response to get the key
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(response.toString());
                    JsonNode successNode = rootNode.path(0).path("success");
                    if (!successNode.isMissingNode()) {
                        bridgeKey = successNode.path("username").asText();
                        System.out.println("Hue Bridge Key: " + bridgeKey);
                    }
                    return bridgeKey;
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

    }