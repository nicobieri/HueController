package teko.ch.zigbee.HueGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    public MainFrame() {
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        this.add(cardsPanel); // Add the cardsPanel to the JFrame
        // Initialize other components of MainFrame if needed...
    }

    public void addPanel(JPanel panel, String name) {
        cardsPanel.add(panel, name); // Add the panel to cardsPanel
    }

    public void switchToPanel(String name) {
        cardLayout.show(cardsPanel, name); // Switch between cards in cardsPanel
    }

}
