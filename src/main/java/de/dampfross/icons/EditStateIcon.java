package de.dampfross.icons;

import de.dampfross.editor.EditState;
import de.dampfross.geometry.Hexagon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class EditStateIcon implements Icon {
    private static final double RADIUS = 11;
    private static final int LENGTH = 18;
    private final Hexagon innerHexagon = new Hexagon(new Point2D.Double(0, 0), 0.8 * RADIUS);
    private final Hexagon outerHexagon = new Hexagon(new Point2D.Double(0, 0), RADIUS);
    private static final Line2D.Double line = new Line2D.Double(0, 0, LENGTH, 0);

    private final EditState editState;

    public EditStateIcon(EditState editState) {
        this.editState = editState;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        final Graphics2D g2d = (Graphics2D) g.create();

        if (editState.isEdge()) {
            g2d.translate(x + getIconWidth() / 2 - LENGTH / 2, y + getIconHeight() / 2);
            g2d.setColor(editState.getHexEdgeType().getColor());
            g2d.draw(line);
        } else {
            g2d.translate(x + getIconWidth() / 2, y + getIconHeight() / 2);
            g2d.setColor(editState.getHexEntityType().getOuterFill());
            g2d.fill(outerHexagon);
            g2d.setColor(editState.getHexEntityType().getInnerFill());
            g2d.fill(innerHexagon);
        }

        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return 22;
    }

    @Override
    public int getIconHeight() {
        return 19;
    }
}
