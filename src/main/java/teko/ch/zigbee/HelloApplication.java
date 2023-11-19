package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.baseApi.ConfigManager;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.hueBridgeConnector;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
  //  @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // stage.setTitle("Hello!");
        // stage.setScene(scene);
        // stage.show();

        String configFilePath = "config.txt";
        Map<String, String> configData = new HashMap<>();
        Map<String, String> readData = ConfigManager.readConfig(configFilePath);

        String ipAddress = readData.get("IP");
        String bridgeKey = readData.get("Key");


        if (ipAddress != null && bridgeKey != null) {
            String bridgeBaseUrl = "http://" + ipAddress + "/api/"; // Replace with your Hue Bridge base URL


            HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, bridgeKey);

            try {
                controller.setLampState(1, "on", "true");
                controller.setLampState(1, "xy", "[0.20,0.15]");

                controller.getLampState(1);


                // Example: Turn off lamp 2
                //controller.setLampState(2, "true");

                // Example: Turn off lamp 3
                //controller.setLampState(3, "true");

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("finish");

        }


        ipAddress = hueBridgeConnector.getMyIP();
        System.out.println("Hue Bridge IP: " + ipAddress);

        configData.put("IP", ipAddress);
        ConfigManager.writeConfig(configFilePath, configData);
        System.out.println();


        hueBridgeConnector connector = new hueBridgeConnector();
        //Assuming you want to use the first Bridge IP found
        bridgeKey = connector.getKey();
        configData.put("Key", bridgeKey);
        ConfigManager.writeConfig(configFilePath, configData);

        String ipAddressFromConfig = readData.get("IP");
        String bridgeKeyFromConfig = readData.get("Key");

        System.out.println("config " + ipAddressFromConfig);
        System.out.println("config " + bridgeKeyFromConfig);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hue Controller");
        HueContollerOverlay panel = new HueContollerOverlay();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        launch();

    }

 }