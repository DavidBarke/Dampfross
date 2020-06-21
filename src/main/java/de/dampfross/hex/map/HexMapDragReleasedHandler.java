package de.dampfross.hex.map;

import de.dampfross.hex.coordinates.HexLocation;
import de.dampfross.hex.entity.HexEntity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class HexMapDragReleasedHandler {
    HexMapController hexMapController;

    public HexMapDragReleasedHandler(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    public void dragReleased(MouseEvent start, MouseEvent end) {
        Point2D.Double startPoint = hexMapController.getCamera().transformPoint(
                new Point(start.getX(), start.getY()),
                hexMapController.getWidth() / 2,
                hexMapController.getHeight() / 2
        );

        Point2D.Double endPoint = hexMapController.getCamera().transformPoint(
                new Point(end.getX(), end.getY()),
                hexMapController.getWidth() / 2,
                hexMapController.getHeight() / 2
        );

        double width = endPoint.x - startPoint.x;
        double height = endPoint.y - startPoint.y;
        Rectangle2D.Double rectangle = new Rectangle2D.Double(startPoint.x, startPoint.y, width, height);

        // Visibility with respect to rectangle
        Set<HexLocation> visibleLocations = hexMapController.getHexMap().getCoordinateSystem().
                getVisibleLocations(rectangle, startPoint);

        Set<HexLocation> newLocations = new HashSet<>();
        for (HexLocation hexLocation : visibleLocations) {
            if (!hexMapController.getHexMap().entityMap.containsKey(hexLocation)) {
                newLocations.add(hexLocation);
            }
        }

        for (HexLocation hexLocation : newLocations) {
            hexMapController.getHexMap().addHexEntityAtLocation(hexLocation);
        }
    }
}
