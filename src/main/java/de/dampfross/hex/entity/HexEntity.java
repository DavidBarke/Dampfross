package de.dampfross.hex.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.dampfross.geometry.*;
import de.dampfross.hex.coordinates.HexCoordinateSystem;
import de.dampfross.hex.coordinates.HexDirection;
import de.dampfross.hex.coordinates.HexLocation;
import de.dampfross.hex.edge.HexEdge;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = HexEntitySerializer.class)
public class HexEntity {
    public final HexCoordinateSystem coordinateSystem;
    public final HexLocation location;
    private int[] edgeIds;
    public final Hexagon outerHexagon;
    public final Hexagon innerHexagon;

    private HexEntityType hexEntityType = HexEntityType.EMPTY;

    public HexEntity(HexCoordinateSystem coordinateSystem, HexLocation location, int[] edgeIds) {
        this.coordinateSystem = coordinateSystem;
        this.location = location;
        this.edgeIds = edgeIds;
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

        return new HexEntity(entity.coordinateSystem, entity.location, entity.edgeIds);
    }

    public void draw(Graphics2D g) {
        g.setColor(hexEntityType.getOuterFill());
        g.fill(outerHexagon);
        g.setColor(hexEntityType.getInnerFill());
        g.fill(innerHexagon);
    }

    public HexEdge getClosestEdge(Point2D.Double p, Map<Integer, HexEdge> hexEdgeMap) {
        Map<HexDirection, HexEdge> edgesMap = getEdgesMap(hexEdgeMap);
        return edgesMap.values().stream().min(Comparator.comparing(e -> e.computeDistance(p))).orElse(null);
    }

    public void setHexEntityType(HexEntityType hexEntityType) {
        this.hexEntityType = hexEntityType;
    }

    public HexEntityType getHexEntityType() {
        return hexEntityType;
    }

    public int[] getEdgeIds() {
        return edgeIds;
    }

    public Map<HexDirection, HexEdge> getEdgesMap(Map<Integer, HexEdge> hexEdgeMap) {
        Map<HexDirection, HexEdge> edgesMap = new HashMap<>();
        for (int i = 0; i < 6; ++i) {
            edgesMap.put(HexDirection.fromIndex(i), hexEdgeMap.get(edgeIds[i]));
        }
        return edgesMap;
    }
}
