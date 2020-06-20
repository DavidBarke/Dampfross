package de.dampfross.hexgrid;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class HexGridMouseInputAdapter extends MouseInputAdapter {
    HexGrid hg;

    HexGridMouseInputAdapter(HexGrid hg) {
        this.hg = hg;
    }

    @Override
    // Use mouseReleased in favor of mouseClicked
    public void mouseReleased(MouseEvent e) {
        hg.requestFocusInWindow();

        if (SwingUtilities.isLeftMouseButton(e)) {
            hg.leftClick(new Point(e.getX(), e.getY()));
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            hg.rightClick(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hg.requestFocusInWindow();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hg.mouseMoved(e);
    }
}
