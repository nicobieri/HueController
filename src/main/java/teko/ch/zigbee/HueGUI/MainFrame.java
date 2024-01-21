package teko.ch.zigbee.HueGUI;

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
}
