package de.dampfross.map;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class HexMapMouseInputAdapter extends MouseInputAdapter {
    HexMapClickHandler hexMapClickHandler;
    HexMapController hexMapController;
    HexMapMouseHandler hexMapMouseHandler;

    HexMapMouseInputAdapter(HexMapController hexMapController, HexMapClickHandler hexMapClickHandler,
                            HexMapMouseHandler hexMapMouseHandler) {
        this.hexMapController = hexMapController;
        this.hexMapClickHandler = hexMapClickHandler;
        this.hexMapMouseHandler = hexMapMouseHandler;
    }

    @Override
    // Use mouseReleased in favor of mouseClicked
    public void mouseReleased(MouseEvent e) {
        hexMapController.requestFocusInWindow();

        if (SwingUtilities.isLeftMouseButton(e)) {
            hexMapClickHandler.leftClick(new Point(e.getX(), e.getY()));
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            hexMapClickHandler.rightClick(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hexMapController.requestFocusInWindow();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hexMapMouseHandler.mouseMoved(e);
    }
}
