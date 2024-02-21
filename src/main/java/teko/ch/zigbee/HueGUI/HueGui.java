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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO Layout how it is look like, dont open a new window here! after connection.
public class HueGui extends JPanel {
    private JPanel HueGui;
    private JButton ButtonToConnect;
    private MainFrame mainFrame;
    private JLabel statusLabel;
    private JTextField macAddressField;
    private JButton findMacAddressButton;
    public HueGui(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout

        // Initialize your components here
        initComponents();

        this.add(HueGui, BorderLayout.CENTER);

        ButtonToConnect.addActionListener(e -> connectToBridge());
        findMacAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMacAddressHelpDialog();
            }
        });
    }
    private void connectToBridge() {
        hueBridgeConnector connector = new hueBridgeConnector();
        try {
            String macAddress = macAddressField.getText().trim();
            if (macAddress.isEmpty()) {
                macAddress = "";
            }
            String bridgeKey  = connector.getKey(macAddress);
            String ipAddress = hueBridgeConnector.getMyIP(macAddress);

            System.out.println(bridgeKey  + "  Key" + ipAddress + " IP ");
            if (bridgeKey.contains("link button not pressed")) {
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

                // Directly calling the method to write config data synchronously.
                ConfigManager.writeConfig(configFilePath, configData);



                String bridgeBaseUrl = "http://" + ipAddress + "/api/"; // Replace with your Hue Bridge base URL
                System.out.println(bridgeBaseUrl);

                HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, bridgeKey);
                controller.getAllLamps();

                System.out.println("finish");

                File configFile = new File("config.txt");
                HueMenue hueMenuePanel = new HueMenue(mainFrame);
                if (configFile.exists()) {
                    mainFrame.switchToPanel("HueMenue");
                    hueMenuePanel.refreshPanelContents();
                }
            }

        } catch (IOException e) {
            // Handle exception
            statusLabel.setText("Connection failed: Please click the Button first! Try again!");
            statusLabel.setForeground(Color.RED);
        }
    }
    private void initComponents() {
        // Main panel setup
        HueGui = new JPanel();
        HueGui.setLayout(new BorderLayout());
        HueGui.setBackground(Color.decode("#212630")); // Sets the background color of the panel

        // Top logo
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/teko/ch/zigbee/phillips-hue-mobile-header.png"))).getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setVerticalAlignment(JLabel.TOP);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Center panel for the bridge button image
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Transparent
        centerPanel.add(Box.createVerticalGlue()); // This will add space above and push the contents to the middle

        // Instruction label
        JLabel instructionLabel = new JLabel("Click The Button on your Hue Bridge", SwingConstants.CENTER);
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font(instructionLabel.getFont().getName(), Font.BOLD, 22));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        centerPanel.add(instructionLabel);

        // Image label
        ImageIcon bridgeButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/teko/ch/zigbee/BridgeButton.png")));
        JLabel bridgeButtonImageLabel = new JLabel(bridgeButtonIcon);
        bridgeButtonImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(bridgeButtonImageLabel);

        // Bottom panel for MAC address input and connect button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // MAC address label
        JLabel macAddressLabel = new JLabel("Manually add the MAC Address to find the Bridge's IP for the connection:");
        macAddressLabel.setForeground(Color.WHITE); // Set the text color to white
        macAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        macAddressLabel.setBorder(BorderFactory.createEmptyBorder(10, 100, 20, 100));
        bottomPanel.add(macAddressLabel);

        // MAC address input field
        macAddressField = new JTextField("xx-xx-xx-xx-xx-xx");
        macAddressField.setMaximumSize(new Dimension(200, 50));
        macAddressField.setHorizontalAlignment(JTextField.CENTER);
        macAddressField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        macAddressField.setOpaque(false);
        macAddressField.setForeground(Color.WHITE);
        macAddressField.getCaret().setBlinkRate(0); // Stop the caret from blinking to look like a placeholder
        macAddressField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (macAddressField.getText().equals("xx-xx-xx-xx-xx-xx")) {
                    macAddressField.setText("");
                    macAddressField.getCaret().setBlinkRate(500); // Restore blink rate when field is focused
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (macAddressField.getText().isEmpty()) {
                    macAddressField.setText("XX-XX-XX-XX-XX-XX");
                    macAddressField.getCaret().setBlinkRate(0); // Stop the caret from blinking when field loses focus
                }
            }
        });
        bottomPanel.add(macAddressField);

        // Find MAC address button
        findMacAddressButton = new JButton("<html><u>How to find my MAC address?</u></html>");
        findMacAddressButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        findMacAddressButton.setBorderPainted(false);
        findMacAddressButton.setContentAreaFilled(false);
        findMacAddressButton.setForeground(Color.WHITE);
        findMacAddressButton.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        bottomPanel.add(findMacAddressButton);

        // Connect button
        ButtonToConnect = new JButton("Connect");
        ButtonToConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(ButtonToConnect);

        // Status label
        statusLabel = new JLabel("Status: Not connected", JLabel.CENTER);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        bottomPanel.add(statusLabel);

        // Add sub-panels to main panel
        HueGui.add(logoLabel, BorderLayout.NORTH);
        HueGui.add(centerPanel, BorderLayout.CENTER);
        HueGui.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void openMacAddressHelpDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("How to Find Your MAC Address");

        // Corrected resource path assuming the image is placed in `src/main/resources/icons/switch-off.png`
        String resourcePath = "/teko/ch/zigbee/HueBridge.png";
        URL imageUrl = getClass().getResource(resourcePath);
        if (imageUrl == null) {
            System.err.println("Resource not found: " + resourcePath);
            return; // Exit the method if the resource cannot be found
        }
        ImageIcon imageIcon = new ImageIcon(imageUrl);

        JLabel label = new JLabel(imageIcon);
        dialog.add(label);
        dialog.pack(); // Adjust dialog size to fit the image
        dialog.setLocationRelativeTo(this); // Center dialog relative to the main window
        dialog.setVisible(true);
    }

}
