package de.dampfross.map;

import de.dampfross.geometry.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HexEntity {
    public final HexCoordinateSystem coordinateSystem;
    public final HexLocation location;
    public final Map<HexDirection, HexEdge> edges = new HashMap<HexDirection, HexEdge>();
    public final Hexagon outerHexagon;
    public final Hexagon innerHexagon;

    private HexEntityType hexEntityType = HexEntityType.EMPTY;

    HexEntity(HexCoordinateSystem coordinateSystem, HexLocation location) {
        this.coordinateSystem = coordinateSystem;
        this.location = location;
        outerHexagon = new Hexagon(coordinateSystem, location);
        innerHexagon = new Hexagon(coordinateSystem, location, 0.8);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof HexEntity)) return false;

        HexEntity e = (HexEntity) o;

        return e.location == this.location;
    }

    public static HexEntity copy(HexEntity entity) {
        if (entity == null) return null;

        return new HexEntity(entity.coordinateSystem, entity.location);
    }

    public void buildEdges(HexMap hexMap) {
        for (HexDirection direction : HexDirection.values()) {
            if (!edges.containsKey(direction)) {
                HexEntity neighborOrNull = hexMap.getEntityOrNull(location.getNeighbor(direction));
                HexEdge edge = new HexEdge(this, neighborOrNull, direction);

                edges.put(direction, edge);

                if (neighborOrNull != null) {
                    neighborOrNull.addEdge(direction.oppositeDirection(), edge);
                }
            }
        }
    }

    public void addEdge(HexDirection direction, HexEdge edge) {
        if (!(edges.containsKey(direction))) {
            edges.put(direction, edge);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(hexEntityType.getOuterFill());
        g.fill(outerHexagon);
        g.setColor(hexEntityType.getInnerFill());
        g.fill(innerHexagon);
    }

    public HexEdge getClosestEdge(Point2D.Double p) {
        return edges.values().stream().min(Comparator.comparing(e -> e.computeDistance(p))).orElse(null);
    }

    public void setHexEntityType(HexEntityType hexEntityType) {
        this.hexEntityType = hexEntityType;
    }

    public HexEntityType getHexEntityType() {
        return hexEntityType;
    }
}
