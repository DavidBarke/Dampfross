package de.dampfross.hexgrid;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Hexagon extends Path2D.Double {

    public static final int SIDES = 6;

    public final Point2D.Double[] nodes = new Point2D.Double[6];
    public final Point2D.Double center;
    public final double radius;

    public Hexagon(HexCoordinateSystem coordinateSystem, HexCoordinates coordinates) {
        this.center = coordinateSystem.getCoordinates2D(coordinates);
        this.radius = coordinateSystem.getRadius();

        for (int i = 0; i < SIDES; ++i) {
            nodes[i] = hexPoint(center, radius, i);
        }

        moveTo(nodes[0].x, nodes[0].y);
        for (int i = 1; i < 6; ++i) {
            lineTo(nodes[i].x, nodes[i].y);
        }
        closePath();
    }

    public Hexagon(HexCoordinateSystem coordinateSystem, HexCoordinates coordinates, double percentage) {
        this.center = coordinateSystem.getCoordinates2D(coordinates);
        this.radius = coordinateSystem.getRadius() * percentage;

        for (int i = 0; i < SIDES; ++i) {
            nodes[i] = hexPoint(center, radius, i);
        }

        moveTo(nodes[0].x, nodes[0].y);
        for (int i = 1; i < 6; ++i) {
            lineTo(nodes[i].x, nodes[i].y);
        }
        closePath();
    }

    public Hexagon(Point2D.Double center, double radius) {
        this.center = center;
        this.radius = radius;

        for (int i = 0; i < SIDES; ++i) {
            nodes[i] = hexPoint(center, radius, i);
        }

        moveTo(nodes[0].x, nodes[0].y);
        for (int i = 1; i < 6; ++i) {
            lineTo(nodes[i].x, nodes[i].y);
        }
        closePath();
    }

    public static Point2D.Double hexPoint(Point2D.Double center, double radius, int i) {
        double angleDeg = 60 * i;
        double angleRad = Math.PI / 180 * angleDeg;

        double x = center.x + radius * Math.cos(angleRad);
        double y = center.y + radius * Math.sin(angleRad);

        return new Point2D.Double(x, y);
    }

    public Point2D.Double[] getNodesInDirection(HexDirection direction) {
        Point2D.Double[] nodesOnEdge = new Point2D.Double[2];

        int firstIndex, secondIndex;

        switch (direction) {
            case NORTH_EAST:
                firstIndex = 0;
                secondIndex = 5;
                break;
            case NORTH:
                firstIndex = 5;
                secondIndex = 4;
                break;
            case NORTH_WEST:
                firstIndex = 4;
                secondIndex = 3;
                break;
            case SOUTH_WEST:
                firstIndex = 3;
                secondIndex = 2;
                break;
            case SOUTH:
                firstIndex = 2;
                secondIndex = 1;
                break;
            case SOUTH_EAST:
                firstIndex = 1;
                secondIndex = 0;
                break;
            default:
                throw new RuntimeException("Illegal direction");
        }

        nodesOnEdge[0] = nodes[firstIndex];
        nodesOnEdge[1] = nodes[secondIndex];

        return nodesOnEdge;
    }
}
