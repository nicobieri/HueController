package teko.ch.zigbee.HueGUI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.readData;
import teko.ch.zigbee.components.CIEColorSlider;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HueMenue extends JPanel {
    private JPanel leftPanel; // Use directly as instance variable
    private JPanel rightPanel; // Use directly as instance variable
    private JTextArea textArea; // Text area for displaying text
    private HueBridgeController controller; // Instance variable for controller

    public HueMenue() throws IOException {
        this.setLayout(new BorderLayout());

        initializeController();
        initializeLeftPanel();
        initializeRightPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(0.7);
        splitPane.setResizeWeight(0.7);

        this.add(splitPane, BorderLayout.CENTER);

        controller.setAllLamps();
    }

    private void initializeController() throws IOException {
        readData read = new readData();
        String ip = read.getIpAddress();
        String key = read.getBridgeKey();
        String bridgeBaseUrl = "http://" + ip + "/api/";
        controller = new HueBridgeController(bridgeBaseUrl, key);
    }

    private void initializeLeftPanel() throws IOException {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        updateLeftPanel();
    }

    private void updateLeftPanel() throws IOException {
        leftPanel.removeAll(); // Clear existing components

        JsonNode jsonResponse = controller.getAllLamps();
        if (jsonResponse.isObject()) {
            for (JsonNode lampNode : jsonResponse) {
                String name = lampNode.get("name").asText();
                boolean on = lampNode.get("state").get("on").asBoolean();

                JPanel row = new JPanel();

                JLabel label = new JLabel(name);
                String iconName = on ? "switch-on.png" : "switch-off.png";
                JButton button = new JButton(new ImageIcon("src/main/java/teko/ch/zigbee/assets/icons/" + iconName));
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);

                // Toggle state on button click
                button.addActionListener(e -> toggleLampState(name, !on));

                CIEColorSlider colorSlider = new CIEColorSlider(new CIEColorSlider.ColorPickerListener(){
                    @Override
                    public void onColorSelected(double x, double y) {
                        // Verwenden Sie hier die X- und Y-Werte, um die Lampe zu aktualisieren
                        System.out.println("Ausgewählte Farbe: X=" + x + ", Y=" + y);
                        updateLampColor(name, x, y);
                    }
                });

                row.add(colorSlider);
                row.add(label);
                row.add(Box.createHorizontalGlue());
                row.add(button);

                leftPanel.add(row);
            }
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }

    private void initializeRightPanel() throws IOException {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.LIGHT_GRAY);

        updateRightPanel();
    }
    private void updateRightPanel() throws IOException {
        rightPanel.removeAll();
        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(HueMenue.this, "Enter new file name:", "Save As", JOptionPane.PLAIN_MESSAGE);
            if (newName != null && !newName.trim().isEmpty()) {
                copyFile("lights.json", newName.trim() + ".json");
            }
        });
        rightPanel.add(saveButton);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        List<String> jsonFiles = getJsonFileNames("");
        jsonFiles.forEach(fileName -> {
            JButton sceneButton = new JButton(fileName);
            sceneButton.addActionListener(e -> copySceneToLights(fileName + ".json", "lights.json"));
            rightPanel.add(sceneButton);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        });

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void copySceneToLights(String sourceFileName, String destFileName) {
        try {
            Files.copy(Paths.get(sourceFileName), Paths.get(destFileName), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Loaded scene: " + sourceFileName, "Success", JOptionPane.INFORMATION_MESSAGE);

            controller.setAllLamps();
            updateRightPanel();
            updateLeftPanel(); // Refresh left panel with new lamp states
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading the Scene: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void copyFile(String sourceFileName, String destFileName) {
        try {
            Files.copy(Paths.get(sourceFileName), Paths.get(destFileName), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Scene Name: " + destFileName, "Success", JOptionPane.INFORMATION_MESSAGE);

            updateLeftPanel();
            updateRightPanel();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving the Scene: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private List<String> getJsonFileNames(String directoryPath) {
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            return walk.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(p -> p.getFileName().toString())
                    .filter(filename -> !filename.equals("lights.json"))
                    .map(filename -> filename.replaceAll("\\.json$", ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void toggleLampState(String lampName, boolean newState) {
        try {
            Path path = Paths.get("lights.json");
            String content = new String(Files.readAllBytes(path));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);

            // Assuming your JSON structure, find the lamp and update its state
            if (root.isObject()) {
                for (JsonNode lampNode : root) {
                    String name = lampNode.get("name").asText();
                    if (name.equals(lampName)) {
                        ((ObjectNode) lampNode.get("state")).put("on", newState);
                        break;
                    }
                }
            }

            // Write the updated JSON back to lights.json
            Files.write(path, mapper.writeValueAsBytes(root));

            // Update lamps via controller
            controller.setAllLamps(); // Ensure this method reads from lights.json and updates the lamps

            // Refresh the UI
            updateLeftPanel();
            updateRightPanel();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating the lamp state: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateLampColor(String lampName, double x , double y) {
        try {
            Path path = Paths.get("lights.json");
            String content = new String(Files.readAllBytes(path));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);

            // Find the lamp by name and update its xy color
            boolean found = false;
            for (JsonNode node : root) {
                if (node.path("name").asText().equals(lampName)) {
                    // This is your lamp node
                    ObjectNode stateNode = (ObjectNode) node.path("state");
                    // Replace the xy array
                    stateNode.putArray("xy").add(x).add(y);
                    found = true;
                    break;
                }
            }

            if (found) {
                // Write the updated JSON back to the file
                Files.write(path, mapper.writeValueAsBytes(root));
                // Trigger an update to the lamps
                controller.setAllLamps();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating the lamp color: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
