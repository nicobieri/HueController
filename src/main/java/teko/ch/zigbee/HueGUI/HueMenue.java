package teko.ch.zigbee.HueGUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import java.awt.*;

public class HueMenue extends JPanel {
    private JPanel HueMenuePanel;
    private JTextArea textArea;

    public HueMenue() {
        HueMenuePanel = new JPanel();
        HueMenuePanel.setBackground(new Color(128, 0, 128)); // Set purple background
        HueMenuePanel.setOpaque(true); // Set the parent panel to be opaque
        this.setLayout(new BorderLayout());
        this.add(HueMenuePanel, BorderLayout.CENTER);
        textArea = new JTextArea();
        add(textArea);
        textArea.setPreferredSize(new Dimension(400, 300));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
    }

    public void updateContent(JsonNode jsonData) {
        // Convert JSON data to a string and set it in the text area
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String formattedJson = null;
        try {
            formattedJson = objectMapper.writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
/// TODO trying to update the text
        // Set the formatted JSON in the JTextArea
        textArea.setText(formattedJson);
        String finalFormattedJson = formattedJson;
        SwingUtilities.invokeLater(() -> {
            textArea.setText(finalFormattedJson);
        });

        revalidate();
        repaint();
    }
    // Additional methods...
}

