package HexGrid;

import Editor.EditState;
import Utilities.Distances;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class HexEdge extends Path2D.Double {
    public final HexEntity e1;
    public final HexEntity e2;

    private HexEdgeType edgeType = HexEdgeType.DEFAULT;

    boolean isBoundaryEdge = false;

    public final Point2D.Double[] nodes;

    // Direction with respect to e1
    HexDirection direction;

    public HexEdge(HexEntity e1, HexEntity e2, HexDirection direction) {
        this.e1 = e1;
        this.e2 = e2;

        this.direction = direction;

        if (e2 == null) isBoundaryEdge = true;

        nodes = e1.outerHexagon.getNodesInDirection(direction);

        moveTo(nodes[0].x, nodes[0].y);
        lineTo(nodes[1].x, nodes[1].y);
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

        return new HexEdge(edge.e1, edge.e2, edge.direction);
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
}
