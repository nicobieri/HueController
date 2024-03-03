package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.HueGUI.HueGui;
import teko.ch.zigbee.HueGUI.HueMenue;
import teko.ch.zigbee.HueGUI.MainFrame;

import javax.swing.*;
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

        MainFrame mainFrame = new MainFrame();
        HueGui hueGuiPanel = new HueGui(mainFrame);
        HueMenue hueMenuePanel = new HueMenue(mainFrame);


        // Add the panels to the main frame
        mainFrame.addPanel(hueGuiPanel, "HueGui");
        mainFrame.addPanel(hueMenuePanel, "HueMenue");

        // Check if config file exists and switch to the appropriate panel
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            mainFrame.switchToPanel("HueMenue");
        } else {
            mainFrame.switchToPanel("HueGui");
        }

        // Set up and display the main frame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

        launch();
    }
 }