package de.dampfross.hex.edge;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.dampfross.hex.coordinates.HexDirection;
import de.dampfross.hex.entity.HexEntity;
import de.dampfross.utilities.Distances;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

@JsonSerialize(using = HexEdgeSerializer.class)
public class HexEdge extends Path2D.Double {
    // id can't be used to check for equality. The id should be unique inside within all edges in hexMap.edges
    private final int id;

    public final HexEntity e1;
    public HexEntity e2;

    private HexEdgeType edgeType = HexEdgeType.DEFAULT;

    boolean isBoundaryEdge = false;

    public final Point2D.Double[] nodes;

    // Direction with respect to e1
    HexDirection direction;

    public HexEdge(int id, HexEntity e1, HexEntity e2, HexDirection direction) {
        this.id = id;

        this.e1 = e1;
        this.e2 = e2;

        this.direction = direction;

        if (e2 == null) isBoundaryEdge = true;

        nodes = e1.outerHexagon.getNodesInDirection(direction);

        moveTo(nodes[0].x, nodes[0].y);
        lineTo(nodes[1].x, nodes[1].y);
    }

    public void addHexEntity(HexEntity e2) {
        if (this.e2 != null) throw new RuntimeException("HexEdge is already connected to two entities.");

        this.e2 = e2;
        isBoundaryEdge = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof HexEdge)) return false;

        HexEdge e = (HexEdge) o;

        return e.e1 == this.e1 && e.e2 == this.e2 && direction == e.direction;
    }

    @Override
    public int hashCode() {
        if (e2 == null) {
            return e1.location.hashCode();
        } else {
            return e1.location.hashCode() + e2.location.hashCode();
        }
    }

    public static HexEdge copy(HexEdge edge) {
        if (edge == null) return null;

        // id is -1 as it has no meaning with respect to the current hexMap
        return new HexEdge(-1, edge.e1, edge.e2, edge.direction);
    }

    public void draw(Graphics2D g) {
        Stroke prevStroke = g.getStroke();
        Color prevColor = g.getColor();

        g.setStroke(new BasicStroke(edgeType.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g.setColor(edgeType.getColor());

        g.draw(this);

        g.setStroke(prevStroke);
        g.setColor(prevColor);
    }

    public HexEdgeType getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(HexEdgeType edgeType) {
        this.edgeType = edgeType;
    }

    public String toString() {
        if (e2 == null) {
            return "Boundary Edge: " + e1.location + ", " + direction;
        } else {
            return "Edge: " + e1.location + ", " + direction;
        }
    }

    public double computeDistance(Point2D.Double p) {
        return Distances.perpendicular(new Line2D.Double(nodes[0], nodes[1]), p);
    }

    public int getId() {
        return id;
    }
}
