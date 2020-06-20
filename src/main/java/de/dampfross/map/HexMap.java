package de.dampfross.map;

import de.dampfross.utilities.Camera;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class HexMap {
    public HexCoordinateSystem coordinateSystem = new HexCoordinateSystem(200);
    Map<HexCoordinates, HexEntity> entityMap = new HashMap<>();
    Set<HexEdge> edges = new HashSet<>();

    public HexMap() {
        int steps = 50;

        for (int q = 0; q < steps; ++q) {
            for (int r = 0; r < steps; ++r) {
                HexLocation hexLocation = new HexLocation(q, r);
                entityMap.put(hexLocation, new HexEntity(coordinateSystem, hexLocation));
            }
        }

        for (HexEntity e : entityMap.values()) {
            e.buildEdges(this);
            edges.addAll(e.edges.values());
        }
    }

    public Set<HexEntity> getVisibleEntities(Shape clip, Point2D.Double upperLeftPoint) {
        Set<HexEntity> visibleEntities = new HashSet<HexEntity>();
        Set<HexLocation> visibleLocations = coordinateSystem.getVisibleLocations(clip, upperLeftPoint);
        for (HexCoordinates coordinates : visibleLocations) {
            HexEntity entity = entityMap.get(coordinates);
            if (entity != null) visibleEntities.add(entity);
        }
        return visibleEntities;
    }

    public Set<HexEdge> getVisibleEdges(Set<HexEntity> visibleEntities) {
        Set<HexEdge> visibleEdges = new HashSet<HexEdge>();
        for (HexEntity entity : visibleEntities) {
            visibleEdges.addAll(entity.edges.values());
        }
        return visibleEdges;
    }

    public HexEntity getEntityAt(Point2D.Double userPoint) {
        HexCoordinates coordinates = coordinateSystem.getHexFromCoordinates2D(userPoint);
        return getEntityOrNull(coordinates);
    }

    public HexEntity getEntityAt(Point screenPoint, Camera camera, int width, int height) {
        Point2D.Double p = camera.transformPoint(screenPoint, width / 2, height / 2);
        return getEntityAt(p);
    }

    public HexEdge getClosestEdge(Point screenPoint, Camera camera, int width, int height) {
        Point2D.Double p = camera.transformPoint(screenPoint, width / 2, height / 2);
        HexEntity entity = getEntityAt(p);
        if (entity == null) return null;
        return entity.getClosestEdge(p);
    }

    public HexEntity getEntityOrNull(HexCoordinates coordinates) {
        return entityMap.get(coordinates);
    }
}
