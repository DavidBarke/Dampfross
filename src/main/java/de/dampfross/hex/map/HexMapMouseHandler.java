package de.dampfross.hex.map;

import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HexMapMouseHandler {
    HexMapController hexMapController;

    public HexMapMouseHandler(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    public void mouseMoved(MouseEvent e) {
        hoverClosestEdge(e);
        hoverClosestEntity(e);
    }

    public void hoverClosestEdge(MouseEvent e) {
        if (!hexMapController.getEditState().isEdge()) {
            hexMapController.setHoverEdge(null);
            return;
        }

        HexEdge hoverEdge = HexEdge.copy(hexMapController.getHexMap().getClosestEdge(
           new Point(e.getX(), e.getY()),
           hexMapController.getCamera(),
           hexMapController.getWidth(),
           hexMapController.getHeight()
        ));

        if (hoverEdge != null) {
            hoverEdge.setEdgeType(hexMapController.getEditState().getHexEdgeType());
        }

        hexMapController.setHoverEdge(hoverEdge);
    }

    public void hoverClosestEntity(MouseEvent e) {
        if (!hexMapController.getEditState().isEntity()) {
            hexMapController.setHoverEntity(null);
            return;
        }

        HexEntity hoverEntity = HexEntity.copy(hexMapController.getHexMap().getEntityAt(
            new Point(e.getX(), e.getY()),
            hexMapController.getCamera(),
            hexMapController.getWidth(),
            hexMapController.getHeight()
        ));

        if (hoverEntity != null) {
            hoverEntity.setHexEntityType(hexMapController.getEditState().getHexEntityType());
        }

        hexMapController.setHoverEntity(hoverEntity);
    }
}
