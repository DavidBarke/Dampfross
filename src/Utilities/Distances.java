package Utilities;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public final class Distances {

    public static double perpendicular(Line2D.Double line, Point2D.Double point) {
        double nominator =
                (line.y2 - line.y1) * point.x - (line.x2 - line.x1) * point.y + line.x2 * line.y1 - line.y2 * line.x1;
        double denominator = Math.pow(line.y2 - line.y1, 2) + Math.pow(line.x2 - line.x1, 2);
        return Math.abs(nominator) / Math.sqrt(denominator);
    }
}
