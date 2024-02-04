package teko.ch.zigbee.HueGUI;

import javax.swing.*;
import java.awt.*;

public class HueMenue extends JPanel {
    private JPanel HueMenuePanel;
    private JTextArea textArea; // Text area for displaying text

    public HueMenue() {
        this.setLayout(new BorderLayout());

        // Left side panel for the lamp controls
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        for (int i = 1; i <= 7; i++) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel label = new JLabel("Lampe " + i);
            JButton button = new JButton(new ImageIcon("src/main/java/teko/ch/zigbee/assets/icons/switch-on.png"));
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);

            row.add(label);
            row.add(Box.createHorizontalGlue());
            row.add(button);

            leftPanel.add(row);
        }

        // Right side panel for the scenes
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.LIGHT_GRAY); // Different background to distinguish the areas

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
    }
}