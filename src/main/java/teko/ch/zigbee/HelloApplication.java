package teko.ch.zigbee;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.HueGUI.HueGui;
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

    }

    public static void main(String[] args) {
        // JFrame frame = new JFrame("Hue Controller");
        // HueContollerOverlay panel = new HueContollerOverlay();
       // HueGui panel = new HueGui();
       // frame.add(panel);
       // frame.getContentPane().add(panel);
       // frame.pack();
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);

        JFrame frame = new JFrame("Hue Controller");
        HueGui panel = new HueGui();
        frame.setContentPane(panel); // Use this instead of frame.add(panel)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // This will size the frame based on the preferred sizes of its components
        frame.setVisible(true);

        launch();

    }

 }