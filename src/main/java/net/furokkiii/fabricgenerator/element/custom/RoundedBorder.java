package net.furokkiii.fabricgenerator.element.custom;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        int adjustedX = x + thickness;
        int adjustedY = y + thickness;
        int adjustedWidth = width - (2 * thickness);
        int adjustedHeight = height - (2 * thickness);
        g2.drawRoundRect(adjustedX, adjustedY, adjustedWidth, adjustedHeight, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + 5, thickness + 5, thickness + 5, thickness + 5);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness + 5;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
