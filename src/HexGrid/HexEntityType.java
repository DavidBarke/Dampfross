package HexGrid;

import Editor.EditState;

import java.awt.*;

public enum HexEntityType {
    EMPTY (Color.WHITE, Color.WHITE),
    CITY (Color.WHITE, Color.RED),
    MOUNTAIN (new Color(130, 84, 16), new Color(130, 84, 16)),
    LAKE (Color.BLUE, Color.BLUE);

    private final Color innerFill;
    private final Color outerFill;

    HexEntityType(Color innerFill, Color outerFill) {
        this.innerFill = innerFill;
        this.outerFill = outerFill;
    }

    public Color getInnerFill() {
        return this.innerFill;
    }

    public Color getOuterFill() {
        return this.outerFill;
    }

    public EditState getEditState() {
        switch (this) {
            case EMPTY:
                return EditState.EMPTY;
            case CITY:
                return EditState.CITY;
            case MOUNTAIN:
                return EditState.MOUNTAIN;
            case LAKE:
                return EditState.LAKE;
            default:
                return null;
        }
    }
}
