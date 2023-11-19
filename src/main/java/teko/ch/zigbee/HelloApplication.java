package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.baseApi.ConfigManager;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.hueBridgeConnector;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class HelloApplication extends Application {
  //  @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // stage.setTitle("Hello!");
        // stage.setScene(scene);
        // stage.show();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        String configFilePath = "config.txt";
        Map<String, String> configData = new HashMap<>();
        Map<String, String> readData = ConfigManager.readConfig(configFilePath);


        String ipAddress = readData.get("IP");
        String bridgeKey = readData.get("Key");
        hueBridgeConnector connector = new hueBridgeConnector();

        if (ipAddress == null && bridgeKey == null) {
            ipAddress = hueBridgeConnector.getMyIP();
            bridgeKey = connector.getKey();

            configData.put("IP", ipAddress);
            configData.put("Key", bridgeKey);

            Runnable task = () -> ConfigManager.writeConfig(configFilePath, configData);

            executorService.schedule(task, 2, TimeUnit.SECONDS);


        }
        String bridgeBaseUrl = "http://" + ipAddress + "/api/"; // Replace with your Hue Bridge base URL

        HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, bridgeKey);




        System.out.println("finish");
        try {
            controller.setLampState(1, "on", "true");
            controller.setLampState(1, "xy", "[0.20,0.45]");

            controller.getLampState(1);



        } catch (IOException e) {
            e.printStackTrace();
        }

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