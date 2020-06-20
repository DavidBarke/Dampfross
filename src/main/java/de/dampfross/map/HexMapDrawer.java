package de.dampfross.map;

import de.dampfross.utilities.Camera;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Set;

public final class HexMapDrawer {
    private static final Font DEFAULT_FONT = new Font("TimesNewRoman", Font.PLAIN, 70);

    public static void draw(Graphics g, Camera camera, HexMapController hexMapController) {
        Graphics2D g2d = (Graphics2D) g;

        Shape clip = camera.getClip(hexMapController.getWidth(), hexMapController.getHeight());
        Shape userClip = camera.getUserClip(hexMapController.getWidth(), hexMapController.getHeight());

        g2d.setClip(clip);

        Font prevFont = g2d.getFont();
        g2d.setFont(DEFAULT_FONT);

        g2d.transform(camera.getTransform(
                hexMapController.getWidth() / 2,
                hexMapController.getHeight() / 2)
        );

        Point2D.Double visiblePoint = camera.transformPoint(
                new Point(
                        hexMapController.getWidth() / 2,
                        hexMapController.getHeight() / 2
                ) ,
                hexMapController.getWidth() / 2,
                hexMapController.getHeight() / 2
        );

        // Draw visible entities and edges
        HexMap hexMap = hexMapController.getHexMap();
        Set<HexEntity> visibleEntities = hexMap.getVisibleEntities(userClip, visiblePoint);
        Set<HexEdge> visibleEdges = hexMap.getVisibleEdges(visibleEntities);

        for (HexEntity entity : visibleEntities) {
            entity.draw(g2d);
        }

        for (HexEdge edge : visibleEdges) {
            edge.draw(g2d);
        }

        if (hexMapController.getHoverEdge() != null) hexMapController.getHoverEdge().draw(g2d);
        if (hexMapController.getHoverEntity() != null) hexMapController.getHoverEntity().draw(g2d);

        g2d.setFont(prevFont);
    }
}
