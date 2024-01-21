package teko.ch.zigbee.HueGUI;

import javax.swing.*;
import java.awt.*;

public class HueMenue extends JPanel {
    private JPanel HueMenuePanel;

    public HueMenue() {
        HueMenuePanel = new JPanel();
        HueMenuePanel.setBackground(new Color(128, 0, 128)); // Set purple background
        this.setLayout(new BorderLayout());
        this.add(HueMenuePanel, BorderLayout.CENTER);
    }

    // Additional methods...
}
