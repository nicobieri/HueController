package teko.ch.zigbee.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO x y values default values of the lamp status and slo the xy value in the picker!!

public class CIEColorSlider extends JSlider {
    private double xValue = 0.5; // Default value for X
    private double yValue = 0.5; // Default value for Y
    private final int circleDiameter = 75; // Diameter of the color circle reduced to a quarter
    private final ColorPickerListener listener; // Listener interface for changes
    private BufferedImage backgroundImage;
    private Point cursorPosition; // Position of the cursor (black dot)

    public interface ColorPickerListener {
        void onColorSelected(double x, double y);
    }

    public CIEColorSlider(ColorPickerListener listener) {
        this.listener = listener;
        setPreferredSize(new Dimension(circleDiameter, circleDiameter));
        cursorPosition = new Point(circleDiameter / 2, circleDiameter / 2); // Initialize cursor in the center
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateCursorPosition(e.getX(), e.getY());
            }
        };
        addMouseListener(ma);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateCursorPosition(e.getX(), e.getY());
            }
        });
        try {
            backgroundImage = ImageIO.read(new File("src/main/java/teko/ch/zigbee/assets/icons/colorPicker.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    private void updateCursorPosition(int x, int y) {
        // Ensure the position is within the circle's bounds
        double radius = circleDiameter / 2.0;
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        double dx = x - centerX;
        double dy = y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > radius) {
            double angle = Math.atan2(dy, dx);
            dx = radius * Math.cos(angle);
            dy = radius * Math.sin(angle);
            x = (int)(centerX + dx);
            y = (int)(centerY + dy);
        }

        cursorPosition.setLocation(x, y);
        // Convert back to x and y values in the color space
        this.xValue = (x - radius) / (2 * radius);
        this.yValue = (radius - y) / (2 * radius);

        if (listener != null) {
            listener.onColorSelected(xValue, yValue);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw the background image, clipped to a circle
        if (backgroundImage != null) {
            Shape clip = new Ellipse2D.Double(0, 0, circleDiameter, circleDiameter);
            g2.setClip(clip);
            g2.drawImage(backgroundImage, 0, 0, circleDiameter, circleDiameter, this);
            g2.setClip(null);
        }

        // Draw the cursor (black dot)
        int cursorDiameter = 10; // Size of the cursor dot
        g2.setColor(Color.BLACK);
        g2.fillOval(cursorPosition.x - cursorDiameter / 2, cursorPosition.y - cursorDiameter / 2, cursorDiameter, cursorDiameter);
    }
    public void setInitialValues(double x, double y) {
        this.xValue = x;
        this.yValue = y;
        // Convert x and y to screen coordinates and update cursorPosition
        double radius = circleDiameter / 2.0;
        // Assuming x and y are normalized (0 to 1), scale them to the circle's diameter
        int xPos = (int) (xValue * circleDiameter);
        int yPos = (int) ((1 - yValue) * circleDiameter); // Inverting y because screen coordinates are flipped

        // Adjust position to be relative to the circle's center
        xPos = (int) (radius + (xPos - radius));
        yPos = (int) (radius - (yPos - radius));

        updateCursorPosition(xPos, yPos);
    }

}
