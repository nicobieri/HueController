package teko.ch.zigbee.HueGUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;

    public MainFrame() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    public void addPanel(JPanel panel, String name) {
        this.add(panel, name);
    }

    public void switchToPanel(String name) {
        cardLayout.show(this.getContentPane(), name);
    }

    // Method to update the "HueMenue" panel with JSON data
    public void updateHueMenuePanel(JsonNode jsonData) throws JsonProcessingException {
        // Assuming you have a reference to the "HueMenue" panel
        HueMenue hueMenue = new HueMenue();

        // Update the content of the panel with the JSON data
        hueMenue.updateContent(jsonData);
    }
}
