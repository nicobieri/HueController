package teko.ch.zigbee.HueGUI;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import teko.ch.zigbee.baseApi.ConfigManager;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.hueBridgeConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HueGui extends JPanel {
    private JPanel HueGui;
    private JButton ButtonToConnect;
    private MainFrame mainFrame;
    private JLabel statusLabel;
    public HueGui(MainFrame mainFrame) {
        this.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout
        // ... (Initialize your components)
        this.add(HueGui, BorderLayout.CENTER);

        statusLabel = new JLabel(); // Initialize the status label
        statusLabel.setHorizontalAlignment(JLabel.CENTER); // Center the text
        this.add(statusLabel, BorderLayout.NORTH);

        initComponents();
        ButtonToConnect.addActionListener(e -> connectToBridge());
    }
    private void connectToBridge() {
        hueBridgeConnector connector = new hueBridgeConnector();
        try {
            String bridgeKey  = connector.getKey();
            String ipAddress = hueBridgeConnector.getMyIP();

            System.out.println(bridgeKey  + "  Key" + ipAddress + " IP ");
            if (bridgeKey .contains("link button not pressed")) {
                System.out.println("Error: link button not pressed");
                statusLabel.setText("Connection failed: Please click the Button first! Try again!");
                statusLabel.setForeground(Color.RED);
            } else {
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                String configFilePath = "config.txt";
                Map<String, String> configData = new HashMap<>();
                Map<String, String> readData = ConfigManager.readConfig(configFilePath);

                System.out.println(ipAddress + "  ipAddress  " + bridgeKey + "  bridgeip");

                    configData.put("IP", ipAddress);
                    configData.put("Key", bridgeKey);

                    Runnable task = () -> ConfigManager.writeConfig(configFilePath, configData);

                    executorService.schedule(task, 0, TimeUnit.SECONDS);


                String bridgeBaseUrl = "http://" + ipAddress + "/api/"; // Replace with your Hue Bridge base URL
                System.out.println(bridgeBaseUrl);

                HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, bridgeKey);
                controller.getAllLamps();

                System.out.println("finish");

                File configFile = new File("config.txt");

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


                }
            }

        } catch (IOException e) {
            // Handle exception
            statusLabel.setText("Connection failed: Please click the Button first! Try again!");
            statusLabel.setForeground(Color.RED);
        }
    }
    private void initComponents() {
        // This method will be automatically generated by IntelliJ IDEA
        // It will initialize components based on the .form file
    }


}
