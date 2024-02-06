package teko.ch.zigbee.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class CIEColorSlider extends JSlider {

    public CIEColorSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the gradient
        LinearGradientPaint lgp = new LinearGradientPaint(
                new Point2D.Float(0, getHeight() / 2f),
                new Point2D.Float(getWidth(), getHeight() / 2f),
                new float[]{0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f},
                new Color[]{Color.WHITE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED, Color.BLACK}
        );

        g2d.setPaint(lgp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Calculate the dot's position
        int sliderValue = getValue();
        int trackWidth = getWidth() - getInsets().left - getInsets().right;
        double percent = (double) sliderValue / (getMaximum() - getMinimum());
        int dotX = (int) (percent * trackWidth + getInsets().left);
        int dotY = getHeight() / 2;

        // Use a fixed thumb offset based on the slider's height or a visually fitting value
        int thumbOffset = 8; // This is an assumed value; adjust as needed for your UI

        // Adjust dot position to align with the slider's thumb position
        dotX -= thumbOffset;

        // Ensure the dot stays within the slider bounds
        dotX = Math.max(dotX, getInsets().left);
        dotX = Math.min(dotX, getWidth() - getInsets().right - thumbOffset * 2);

        // Set color and draw the dot
        g2d.setColor(Color.BLACK); // Set the dot color for visibility
        g2d.fillOval(dotX, dotY - 5, 10, 10); // Adjust size as needed

        g2d.dispose();
    }
}