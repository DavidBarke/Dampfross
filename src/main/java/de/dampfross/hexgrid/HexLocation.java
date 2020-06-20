package de.dampfross.hexgrid;

// Cube coordinates
public class HexLocation extends HexCoordinates {
    private final HexCoordinateNeighbors neighbors;

    HexLocation(int x, int y, int z) {
        super(x, y, z);

        neighbors = new HexCoordinateNeighbors( this);
    }

    HexLocation(int q, int r) {
        super(q, r);

        neighbors = new HexCoordinateNeighbors(this);
    }

    HexLocation(double x, double y, double z) {
        super(x, y, z);

        neighbors = new HexCoordinateNeighbors(this);
    }

    public boolean isNeighbor(HexLocation c) {
        return neighbors.contains(c);
    }

    public HexCoordinates getNeighbor(HexDirection direction) {
        return neighbors.getNeighbor(direction);
    }

    public HexCoordinates getNeighbor(int n) {
        return neighbors.getNeighbor(n);
    }

    public HexCoordinates[] getNeighbors() {
        return neighbors.array;
    }
}
