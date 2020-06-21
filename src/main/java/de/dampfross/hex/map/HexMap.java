package de.dampfross.hex.map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.dampfross.hex.coordinates.HexCoordinateSystem;
import de.dampfross.hex.coordinates.HexCoordinates;
import de.dampfross.hex.coordinates.HexDirection;
import de.dampfross.hex.coordinates.HexLocation;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;
import de.dampfross.utilities.Camera;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

@JsonSerialize(using = HexMapSerializer.class)
@JsonDeserialize(using = HexMapDeserializer.class)
public class HexMap {
    private HexCoordinateSystem coordinateSystem = new HexCoordinateSystem(200);

    Map<HexCoordinates, HexEntity> entityMap = new HashMap<>();

    Map<Integer, HexEdge> hexEdgeMap = new HashMap<>();

    int maxId;

    public HexMap() {
        HexLocation hexLocation = new HexLocation(0, 0);
        int[] edgeIds = {0, 1, 2, 3, 4, 5};
        HexEntity hexEntity = new HexEntity(coordinateSystem, hexLocation, edgeIds);
        entityMap.put(hexLocation, hexEntity);
        for (int i = 0; i < 6; ++i) {
            hexEdgeMap.put(i, new HexEdge(i, hexEntity, null, HexDirection.fromIndex(i)));
        }
        maxId = 5;
    }

    public HexMap(Map<HexCoordinates, HexEntity> entityMap, Map<Integer, HexEdge> hexEdgeMap,
                  HexCoordinateSystem coordinateSystem) {
        this.entityMap = entityMap;
        this.hexEdgeMap = hexEdgeMap;
        this.coordinateSystem = coordinateSystem;
        maxId = hexEdgeMap.keySet().stream().max(Integer::compareTo).orElse(0);
        if (maxId == 0) throw new RuntimeException("Maximum of hexEdgeMap could not be determined.");
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
            visibleEdges.addAll(entity.getEdgesMap(hexEdgeMap).values());
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
        return entity.getClosestEdge(p, hexEdgeMap);
    }

    public HexEntity getEntityOrNull(HexCoordinates coordinates) {
        return entityMap.get(coordinates);
    }

    public HexCoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    public void addHexEntityAtLocation(HexLocation location) {
        if (entityMap.containsKey(location)) throw new RuntimeException("Location has already an entity.");

        int[] edgeIds = new int[6];

        Set<HexEdge> neighborEdges = new HashSet<>();
        Map<Integer, HexDirection> newEdgeIds = new HashMap<>();
        for (int i = 0; i < 6; ++i) {
            HexDirection hexDirection = HexDirection.fromIndex(i);
            HexCoordinates neighborCoordinates = location.getNeighbor(hexDirection);
            if (entityMap.containsKey(neighborCoordinates)) {
                HexEntity neighborEntity = entityMap.get(neighborCoordinates);
                Map<HexDirection, HexEdge> directionHexEdgeMap = neighborEntity.getEdgesMap(hexEdgeMap);
                HexEdge neighborEdge = directionHexEdgeMap.get(hexDirection.oppositeDirection());
                neighborEdges.add(neighborEdge);
                edgeIds[i] = neighborEdge.getId();
            } else {
                newEdgeIds.put(++maxId, hexDirection);
                edgeIds[i] = maxId;
            }
        }

        HexEntity hexEntity = new HexEntity(coordinateSystem, location, edgeIds);

        for (Integer id : newEdgeIds.keySet()) {
            hexEdgeMap.put(id, new HexEdge(id, hexEntity, null, newEdgeIds.get(id)));
        }

        for (HexEdge neighborEdge : neighborEdges) {
            neighborEdge.addHexEntity(hexEntity);
        }

        entityMap.put(location, hexEntity);
    }
}
