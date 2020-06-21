package de.dampfross.hex.coordinates;

public enum HexDirection {
    NORTH_EAST (1, -1, 0),
    NORTH (0, -1, 1),
    NORTH_WEST (-1, 0, 2),
    SOUTH_WEST (-1, 1, 3),
    SOUTH (0, 1, 4),
    SOUTH_EAST (1, 0, 5);

    private final int q;
    private final int r;
    private final int index;

    HexDirection(int q, int r, int index) {
        this.q = q;
        this.r = r;
        this.index = index;
    }

    public HexDirection oppositeDirection() {
        switch (this) {
            case NORTH_EAST:
                return SOUTH_WEST;
            case NORTH:
                return SOUTH;
            case NORTH_WEST:
                return SOUTH_EAST;
            case SOUTH_WEST:
                return NORTH_EAST;
            case SOUTH:
                return NORTH;
            case SOUTH_EAST:
                return NORTH_WEST;
        }

        throw new RuntimeException("Illegal direction");
    }

    public int getIndex() {
        return this.index;
    }

    public HexCoordinates getDirection() {
        return new HexCoordinates(q, r);
    }

    public static HexDirection fromIndex(int index) {
        switch (index) {
            case 0:
                return NORTH_EAST;
            case 1:
                return NORTH;
            case 2:
                return NORTH_WEST;
            case 3:
                return SOUTH_WEST;
            case 4:
                return SOUTH;
            case 5:
                return SOUTH_EAST;
            default:
                throw new RuntimeException("index must be between 0 and 5");
        }
    }
}
