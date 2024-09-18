package net.furokkiii.fabricgenerator.element.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedJSpinner extends JSpinner {
    private int cornerRadius;

    public RoundedJSpinner(SpinnerModel model, int cornerRadius) {
        super(model);
        this.cornerRadius = cornerRadius;
        //setMargin(new Insets(5, 10, 5, 10));
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.setClip(clip);
            super.paintComponent(g2);
        } finally {
            g2.dispose();
        }
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint();
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(false);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
