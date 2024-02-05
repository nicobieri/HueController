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

//        jsonFile JsonFileWriter = new jsonFile();
//        jsonFile JsonFileReader = new jsonFile();

        mainFrame.addPanel(hueGuiPanel, "HueGui");
        mainFrame.addPanel(hueMenuePanel, "HueMenue");

        // Check if config file exists and switch to the appropriate panel
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            System.out.println("Config file exists");
//            readData read = new readData();
//            String Ip = read.getIpAddress();
//            String Key = read.getBridgeKey();

//            String bridgeBaseUrl = "http://" + Ip + "/api/";
//            HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, Key);
//            JsonNode jsonResponse = controller.getAllLamps();
//            String jsonFilePath = "lights.json"; // Specify your file path here

//            JsonFileWriter.writeJsonToFile(jsonResponse, jsonFilePath);
            mainFrame.switchToPanel("HueMenue");
//            hueMenuePanel.updateText(String.valueOf(jsonResponse));
//            hueMenuePanel.updateBackgroundColor(50, 0, 100);
            mainFrame.switchToPanel("HueMenue");
//            JsonFileWriter.updateProductBriValue("lights.json", "Pult", 254);
//            try {
//
////                JsonFileWriter.updateProductColor("lights.json", "Hue color lamp 1", 0.2, 0.1);
////                JsonFileWriter.updateProductColor("lights.json", "Pult", 0.3, 0.1);
////                JsonFileWriter.updateProductOn("lights.json", "Pult", true);
//                System.out.println("Updated 'xy' value successfully.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            controller.setAllLamps();


            /// TODO how to change the who Json and send it to change the whole scene?
//            controller.setLampState(1, "on", "true");
//            controller.setLampState(1, "xyz", "[0.10,0.20]");
//            controller.setLampState(5, "xyz", "[0.10,0.20,0.00]");
//            controller.setLampState(5, "xyz", "[0.10,0.20,0.00]");
//
//            controller.getLampState(1);
//            System.out.println(jsonResponse.toString());


        } else {
            System.out.println("Config file does not exist");
            mainFrame.switchToPanel("HueGui");
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
//        try {
//            String jsonFilePath = "lights.json"; // Specify your file path here
////            JsonNode jsonData = JsonFileReader.readJsonFromFile(jsonFilePath);
//
//            // Use jsonData as needed
////            System.out.println(jsonData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error reading JSON file.");
//        }

        launch();
    }

 }