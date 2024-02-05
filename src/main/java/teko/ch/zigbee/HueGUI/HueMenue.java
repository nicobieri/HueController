package teko.ch.zigbee.HueGUI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.jsonFile;
import teko.ch.zigbee.baseApi.readData;


public class HueMenue extends JPanel {
    private JPanel HueMenuePanel;
    private JTextArea textArea; // Text area for displaying text

    public HueMenue() throws IOException {
        this.setLayout(new BorderLayout());

        // Left side panel for the lamp controls
        jsonFile JsonFileWriter = new jsonFile();
        jsonFile JsonFileReader = new jsonFile();
        readData read = new readData();
        String Ip = read.getIpAddress();
        String Key = read.getBridgeKey();

        String bridgeBaseUrl = "http://" + Ip + "/api/";
        HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, Key);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);



        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = controller.getAllLamps();
        String jsonFilePath = "lights.json"; // Specify your file path here

        JsonFileWriter.writeJsonToFile(jsonResponse, jsonFilePath);

        if (jsonResponse.isObject()) {
            for (JsonNode lampNode : jsonResponse) {
                String name = lampNode.get("name").asText(); // Get the lamp name

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                row.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel label = new JLabel(name); // Use the actual lamp name
                JButton button = new JButton(new ImageIcon("src/main/java/teko/ch/zigbee/assets/icons/switch-on.png"));
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);

                row.add(label);
                row.add(Box.createHorizontalGlue());
                row.add(button);

                leftPanel.add(row);
            }
        }

        // Right side panel for the scenes
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Create scene cards
        for (int i = 1; i <= 3; i++) {
            JPanel card = new JPanel();
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            card.add(new JLabel("Szene " + i));
            rightPanel.add(card);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between cards
        }

        // Create a split pane to separate the left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(0.7); // Set the divider at 70% of the width
        splitPane.setResizeWeight(0.7); // The left side gets the extra space on resize

        // Add the split pane to the main panel
        this.add(splitPane, BorderLayout.CENTER);
        // Method to update the text area content
//        public void updateText (String text){
//            textArea.setText(text);
//        }
//        public void updateBackgroundColor ( int r, int g, int b){
//            HueMenuePanel.setBackground(new Color(r, g, b));
//        }
        // Additional methods...

        controller.setAllLamps();
    }
}