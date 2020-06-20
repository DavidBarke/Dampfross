package de.dampfross.editor;

import de.dampfross.hexgrid.HexEdgeType;
import de.dampfross.hexgrid.HexEntityType;

public enum EditState {
    NONE (null, null),
    EMPTY (HexEntityType.EMPTY, null),
    CITY (HexEntityType.CITY, null),
    MOUNTAIN (HexEntityType.MOUNTAIN, null),
    LAKE (HexEntityType.LAKE, null),
    RIVER (null, HexEdgeType.RIVER),
    BORDER (null, HexEdgeType.BORDER);

    private final HexEntityType hexEntityType;
    private final HexEdgeType hexEdgeType;

    EditState(HexEntityType hexEntityType, HexEdgeType hexEdgeType) {
        this.hexEntityType = hexEntityType;
        this.hexEdgeType = hexEdgeType;
    }

    public boolean isEdge() {
        switch (this) {
            case RIVER:
            case BORDER:
                return true;
            default:
                return false;
        }
    }

    public boolean isEntity() {
        switch (this) {
            case EMPTY:
            case CITY:
            case MOUNTAIN:
            case LAKE:
                return true;
            default:
                return false;
        }
    }

    public HexEdgeType getHexEdgeType() {
        return hexEdgeType;
    }

    public HexEntityType getHexEntityType() {
        return hexEntityType;
    }
}
