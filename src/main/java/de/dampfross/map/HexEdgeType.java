package de.dampfross.map;

import java.awt.*;

public enum HexEdgeType {
    DEFAULT (Color.BLACK, 1),
    RIVER (Color.BLUE, 20),
    BORDER (Color.RED, 20);

    private final Color color;
    private final int strokeWidth;

    HexEdgeType(Color color, int strokeWidth) {
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Color getColor() {
        return this.color;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }
}
