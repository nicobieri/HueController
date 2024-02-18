package teko.ch.zigbee;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.HueGUI.HueGui;
import teko.ch.zigbee.HueGUI.HueMenue;
import teko.ch.zigbee.HueGUI.MainFrame;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.jsonFile;
import teko.ch.zigbee.baseApi.readData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // stage.setTitle("Hello!");
        // stage.setScene(scene);
        // stage.show();

    }

    public static void main(String[] args) throws IOException {

        File configFile = new File("config.txt");



        // Check if config file exists and switch to the appropriate panel
        System.out.println(configFile.exists());

        if (configFile.exists()) {
            MainFrame mainFrame = new MainFrame();
            HueGui hueGuiPanel = new HueGui(mainFrame);
            HueMenue hueMenuePanel = new HueMenue();
            mainFrame.addPanel(hueGuiPanel, "HueGui");
            mainFrame.addPanel(hueMenuePanel, "HueMenue");
            mainFrame.switchToPanel("HueMenue");

            mainFrame.switchToPanel("HueMenue");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setVisible(true);


        } else {
            MainFrame mainFrame = new MainFrame();
            HueGui hueGuiPanel = new HueGui(mainFrame);
            mainFrame.addPanel(hueGuiPanel, "HueGui");
            System.out.println("Config file does not exist");
            mainFrame.switchToPanel("HueGui");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setVisible(true);
        }

        launch();
    }

 }