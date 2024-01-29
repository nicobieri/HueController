package teko.ch.zigbee;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.HueGUI.HueGui;
import teko.ch.zigbee.HueGUI.HueMenue;
import teko.ch.zigbee.HueGUI.MainFrame;
import teko.ch.zigbee.baseApi.ConfigManager;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.hueBridgeConnector;
import teko.ch.zigbee.baseApi.readData;

import javax.swing.*;
import java.io.File;
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

    public static void main(String[] args) throws IOException {
        // JFrame frame = new JFrame("Hue Controller");
        // HueContollerOverlay panel = new HueContollerOverlay();
       // HueGui panel = new HueGui();
       // frame.add(panel);
       // frame.getContentPane().add(panel);
       // frame.pack();
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);

        MainFrame mainFrame = new MainFrame();
        HueGui hueGuiPanel = new HueGui(mainFrame);
        HueMenue hueMenuePanel = new HueMenue();

        mainFrame.addPanel(hueGuiPanel, "HueGui");
        mainFrame.addPanel(hueMenuePanel, "HueMenue");

        // Check if config file exists and switch to the appropriate panel
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            System.out.println("Config file exists");
            readData read = new readData();
            String Ip = read.getIpAddress();
            String Key = read.getBridgeKey();

            String bridgeBaseUrl = "http://" + Ip + "/api/";
            HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, Key);
            JsonNode jsonResponse = controller.getAllLamps();
            System.out.println(jsonResponse.toString());
            mainFrame.switchToPanel("HueMenue");
            hueMenuePanel.updateText(String.valueOf(jsonResponse));
            mainFrame.switchToPanel("HueMenue");

        } else {
            System.out.println("Config file does not exist");
            mainFrame.switchToPanel("HueGui");
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

        launch();
    }

 }