package de.dampfross.hexgrid;

import de.dampfross.editor.EditState;
import de.dampfross.transformation.Rotation;
import de.dampfross.transformation.Translation;
import de.dampfross.transformation.Zoom;
import de.dampfross.ui.ContextMenu.HexGridContextMenu;
import de.dampfross.utilities.Camera;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class HexGrid extends JPanel {
    public HexCoordinateSystem coordinateSystem = new HexCoordinateSystem(200);
    Camera camera;

    Map<HexCoordinates, HexEntity> entityMap = new HashMap<>();
    Set<HexEdge> edges = new HashSet<>();

    private static final int STROKE_SMALL = 1;
    private static final int STROKE_BIG = 20;

    private static final Font DEFAULT_FONT = new Font("TimesNewRoman", Font.PLAIN, 70);

    private EditState editState = EditState.MOUNTAIN;

    HexEdge hoverEdge;
    HexEntity hoverEntity;

    HexEntity clickedEntity;
    private List<HexGridClickedEntityListener> clickedEntityListeners = new ArrayList<HexGridClickedEntityListener>();

    public HexGrid() {
        camera = new Camera(20, 0.03);

        int steps = 50;

        for (int q = 0; q < steps; ++q) {
            for (int r = 0; r < steps; ++r) {
                HexLocation coordinates = new HexLocation(q, r);
                if (Math.random() < 0.05) {
                    int cityNumber = (int) (Math.random() * 60);
                    entityMap.put(coordinates, new CityHexEntity(coordinateSystem, coordinates, cityNumber));
                } else {
                    entityMap.put(coordinates, new HexEntity(coordinateSystem, coordinates));
                }
            }
        }

        for (HexEntity e : entityMap.values()) {
            e.buildEdges(this);
            edges.addAll(e.edges.values());
        }

        HexGridMouseInputAdapter adapter = new HexGridMouseInputAdapter(this);
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        this.addMouseWheelListener(new Zoom(camera));
        this.addKeyListener(new Translation(camera));
        this.addKeyListener(new Rotation(camera));

        ActionListener movePerformer = e -> {
            camera.move();
            repaint();
        };

        new Timer(40, movePerformer).start();
    }

    public Set<HexEntity> getVisibleEntities(HexCoordinateSystem coordinateSystem, Shape clip, Point2D.Double upperLeftPoint) {
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Shape clip = camera.getClip(getWidth(), getHeight());
        Shape userClip = camera.getUserClip(getWidth(), getHeight());

        g2d.setClip(clip);

        Font prevFont = g2d.getFont();
        g2d.setFont(DEFAULT_FONT);

        g2d.transform(camera.getTransform(xOff(), yOff()));

        Point2D.Double visiblePoint = camera.transformPoint(
                new Point(getWidth() / 2, getHeight() / 2) , getWidth() / 2, getHeight() / 2
        );

        Set<HexEntity> visibleEntities = getVisibleEntities(coordinateSystem, userClip, visiblePoint);
        Set<HexEdge> visibleEdges = getVisibleEdges(visibleEntities);

        for (HexEntity entity : visibleEntities) {
            entity.draw(g2d);
        }

        for (HexEdge edge : visibleEdges) {
            edge.draw(g2d);
        }

//        for (HexEntity entity : entityMap.values()) {
//            entity.draw(g2d);
//        }
//
//        for (HexEdge edge : edges) {
//            edge.draw(g2d);
//        }

        if (hoverEdge != null) hoverEdge.draw(g2d);
        if (hoverEntity != null) hoverEntity.draw(g2d);

        g2d.setFont(prevFont);
    }

    private int xOff() {
        return getWidth() / 2;
    }

    private int yOff() {
        return getHeight() / 2;
    }

    public HexEntity getEntityAt(Point2D.Double p) {
        HexCoordinates coordinates = coordinateSystem.getHexFromCoordinates2D(p);
        return getEntityOrNull(coordinates);
    }

    public HexEntity getEntityAt(Point screenPoint) {
        Point2D.Double p = camera.transformPoint(screenPoint, xOff(), yOff());
        return getEntityAt(p);
    }

    public HexEdge getClosestEdge(Point screenPoint) {
        Point2D.Double p = camera.transformPoint(screenPoint, xOff(), yOff());
        HexEntity entity = getEntityAt(p);
        if (entity == null) return null;
        return entity.getClosestEdge(p);
    }

    public void leftClick(Point screenPoint) {
        if (editState == EditState.NONE) {
            HexEntity entity = getEntityAt(screenPoint);
            if (entity != null) {
                for (HexGridClickedEntityListener listener : clickedEntityListeners) {
                    listener.setActiveEntity(entity);
                }
            }
        }

        if (editState.isEdge()) {
            HexEdge closestEdge = getClosestEdge(screenPoint);
            if (closestEdge != null) {
                closestEdge.setEdgeType(editState.getHexEdgeType());
            }
        }

        if (editState.isEntity()) {
            HexEntity entity = getEntityAt(screenPoint);

            if (entity != null) {
                entity.setHexEntityType(editState.getHexEntityType());
            }
        }
    }

    public void rightClick(Point screenPoint) {
        HexGridContextMenu contextMenu = new HexGridContextMenu();
        setComponentPopupMenu(contextMenu);
    }

    public HexEntity getEntityOrNull(HexCoordinates coordinates) {
        return entityMap.get(coordinates);
    }

    public void mouseMoved(MouseEvent e) {
        hoverClosestEdge(e);
        hoverClosestEntity(e);
    }

    public void hoverClosestEdge(MouseEvent e) {
        if (!editState.isEdge()) {
            hoverEdge = null;
            return;
        }

        hoverEdge = HexEdge.copy(getClosestEdge(new Point(e.getX(), e.getY())));
        if (hoverEdge != null) {
            hoverEdge.setEdgeType(editState.getHexEdgeType());
        }
    }

    public void hoverClosestEntity(MouseEvent e) {
        if (!editState.isEntity()) {
            hoverEntity = null;
            return;
        }

        hoverEntity = HexEntity.copy(getEntityAt(new Point(e.getX(), e.getY())));
        if (hoverEntity != null) hoverEntity.setHexEntityType(editState.getHexEntityType());
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
    }

    public void addClickedEntityListener(HexGridClickedEntityListener listener) {
        clickedEntityListeners.add(listener);
    }
}
