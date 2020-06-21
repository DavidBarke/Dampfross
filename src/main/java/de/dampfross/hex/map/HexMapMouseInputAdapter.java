package de.dampfross.hex.map;

import de.dampfross.hex.map.HexMapClickHandler;
import de.dampfross.hex.map.HexMapController;
import de.dampfross.hex.map.HexMapMouseHandler;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class HexMapMouseInputAdapter extends MouseInputAdapter {
    HexMapClickHandler hexMapClickHandler;
    HexMapController hexMapController;
    HexMapDragReleasedHandler hexMapDragReleasedHandler;
    HexMapMouseHandler hexMapMouseHandler;

    MouseEvent dragStart;

    HexMapMouseInputAdapter(HexMapController hexMapController, HexMapClickHandler hexMapClickHandler,
                            HexMapMouseHandler hexMapMouseHandler, HexMapDragReleasedHandler hexMapDragReleasedHandler) {
        this.hexMapController = hexMapController;
        this.hexMapClickHandler = hexMapClickHandler;
        this.hexMapMouseHandler = hexMapMouseHandler;
        this.hexMapDragReleasedHandler = hexMapDragReleasedHandler;
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

        if (dragStart != null) {
            hexMapDragReleasedHandler.dragReleased(dragStart, e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hexMapController.requestFocusInWindow();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dragStart = null;
        hexMapMouseHandler.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart == null) dragStart = e;
    }
}
