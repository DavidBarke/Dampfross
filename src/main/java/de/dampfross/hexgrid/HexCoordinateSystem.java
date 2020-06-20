package de.dampfross.hexgrid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class HexCoordinateSystem {
    public static final HexCoordinates originHex = new HexCoordinates(0, 0);
    public static final Point2D.Double origin2D = new Point2D.Double(0, 0);

    // Distance between two hexes that lay on a coordinate axis
    public final double size;

    HexCoordinateSystem(double size) {
        this.size = size;
    }

    public double getRadius() {
        return size / Math.sqrt(3);
    }

    public Point2D.Double getCoordinates2D(HexCoordinates coordinates) {
        double radius = getRadius();
        double x = origin2D.x + (coordinates.q) * 3. / 2. * radius;
        double y = origin2D.y + coordinates.r * size + coordinates.q * size / 2;
        return new Point2D.Double(x, y);
    }

    public HexCoordinates getHexFromCoordinates2D(Point2D.Double coordinates) {
        double q = 2. / 3. * coordinates.x / getRadius();
        double r = (-1. / 3. * coordinates.x + Math.sqrt(3) / 3. * coordinates.y) / getRadius();
        double[] cube = HexCoordinates.axialToCube(q, r);
        return new HexCoordinates(cube[0], cube[1], cube[2]);
    }

    public Set<HexLocation> getVisibleLocations(Shape clip, Point2D.Double upperLeftPoint) {
        Set<HexLocation> visibleLocations = new HashSet<>();

        HexCoordinates upperLeftCoordinates = getHexFromCoordinates2D(
                new Point2D.Double(upperLeftPoint.x, upperLeftPoint.y)
        );
        HexLocation upperLeftLocation = new HexLocation(upperLeftCoordinates.q, upperLeftCoordinates.r);

        findVisibleNeighbors(visibleLocations, upperLeftLocation, clip);

        return visibleLocations;
    }

    public void findVisibleNeighbors(Set<HexLocation> visibleNeighbors, HexLocation location, Shape clip) {
        for (HexCoordinates neighbor : location.getNeighbors()) {
            if (isCenterVisible(neighbor, clip)) {
                HexLocation neighborLocation = new HexLocation(neighbor.q, neighbor.r);;
                if (!visibleNeighbors.contains(neighborLocation)) {
                    visibleNeighbors.add(neighborLocation);
                    findVisibleNeighbors(visibleNeighbors, neighborLocation, clip);
                }
            }
        }
    }

    public boolean isCenterVisible(HexCoordinates coordinates, Shape clip) {
        return clip.contains(getCoordinates2D(coordinates));
    }
}
