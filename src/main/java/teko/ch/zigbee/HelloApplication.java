package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.hueBridgeConnector;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
  //  @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
       // stage.setTitle("Hello!");
       // stage.setScene(scene);
       // stage.show();


        String ipAddress = hueBridgeConnector.getMyIP();
        System.out.println("Hue Bridge IP: " + ipAddress);

        hueBridgeConnector connector = new hueBridgeConnector();
        //Assuming you want to use the first Bridge IP found
        String bridgeKey = connector.getKey();
        System.out.println(bridgeKey);


        String bridgeBaseUrl = "http://" + ipAddress + "/api/"; // Replace with your Hue Bridge base URL
        ///todo Nico

        //String apiKey = "5kAfpCWnRQeUphawHE7yN2Fon4l4ZJjzqvVo788T"; // Replace with your API key

        ///todo Steve
        //String apiKey = "4PCT1TYO3UrSjf7lLsQODWsrjfS-C7m47l0FOCFc"; // Replace with your API key



        HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, bridgeKey);

                try {
                    controller.setLampState(1, "on", "true");
                    controller.setLampState(1, "xy", "[0.20,0.45]");

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