package de.dampfross.map;

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
}
