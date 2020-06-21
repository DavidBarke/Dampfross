package de.dampfross.hex.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dampfross.editor.EditState;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;
import de.dampfross.ui.contextmenu.HexMapContextMenu;

import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HexMapClickHandler {
    HexMapController hexMapController;
    private List<HexMapClickedEntityListener> clickedEntityListeners = new ArrayList<HexMapClickedEntityListener>();

    public HexMapClickHandler(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    public void leftClick(Point screenPoint) {
        if (hexMapController.getEditState() == EditState.NONE) {
            HexEntity entity = hexMapController.getHexMap().getEntityAt(
                    screenPoint,
                    hexMapController.getCamera(),
                    hexMapController.getWidth(),
                    hexMapController.getHeight()
            );
            if (entity != null) {
                for (HexMapClickedEntityListener listener : clickedEntityListeners) {
                    listener.setActiveEntity(entity);
                }
            }
        }

        if (hexMapController.getEditState().isEdge()) {
            HexEdge closestEdge = hexMapController.getHexMap().getClosestEdge(
                    screenPoint,
                    hexMapController.getCamera(),
                    hexMapController.getWidth(),
                    hexMapController.getHeight()
            );
            if (closestEdge != null) {
                closestEdge.setEdgeType(hexMapController.getEditState().getHexEdgeType());
            }
        }

        if (hexMapController.getEditState().isEntity()) {
            HexEntity entity = hexMapController.getHexMap().getEntityAt(
                    screenPoint,
                    hexMapController.getCamera(),
                    hexMapController.getWidth(),
                    hexMapController.getHeight()
            );

            if (entity != null) {
                entity.setHexEntityType(hexMapController.getEditState().getHexEntityType());
            }
        }
    }

    public void rightClick(Point screenPoint) {
        HexMapContextMenu contextMenu = new HexMapContextMenu();
        hexMapController.setComponentPopupMenu(contextMenu);
    }

    public void addClickedEntityListener(HexMapClickedEntityListener listener) {
        clickedEntityListeners.add(listener);
    }
}
