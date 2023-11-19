package teko.ch.zigbee.baseApi;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {


    // Method to write data to the config file
    public static void writeConfig(String filePath, Map<String, String> data) {
        try (PrintWriter out = new PrintWriter(filePath)) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to read data from the config file
    public static Map<String, String> readConfig(String filePath) {
        Map<String, String> result = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    result.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return result;
    }
}
