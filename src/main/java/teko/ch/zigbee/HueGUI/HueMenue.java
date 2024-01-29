package teko.ch.zigbee.HueGUI;

import javax.swing.*;
import java.awt.*;

public class HueMenue extends JPanel {
    private JPanel HueMenuePanel;
    private JTextArea textArea; // Text area for displaying text

    public HueMenue() {
        HueMenuePanel = new JPanel();
        HueMenuePanel.setBackground(new Color(128, 0, 128)); // Set purple background
        this.setLayout(new BorderLayout());
        this.add(HueMenuePanel, BorderLayout.CENTER);

        // Initialize the text area
        textArea = new JTextArea();
        textArea.setEditable(false); // Set to false if you don't want users to edit the text
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Optionally, add a scroll pane to make the text area scrollable
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the text area (wrapped in a scroll pane) to the panel
        HueMenuePanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Method to update the text area content
    public void updateText(String text) {
        textArea.setText(text);
    }
    public void updateBackgroundColor(int r, int g, int b) {
        HueMenuePanel.setBackground(new Color(r, g, b));
    }
    // Additional methods...
}