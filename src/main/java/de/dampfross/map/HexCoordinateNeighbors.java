package de.dampfross.map;

public class HexCoordinateNeighbors {
    private final int NEIGHBORS = 6;
    public final HexCoordinates[] array = new HexCoordinates[NEIGHBORS];

    public HexCoordinateNeighbors(HexCoordinates coordinates) {
        array[0] = coordinates.add(HexDirection.NORTH_EAST.getDirection());
        array[1] = coordinates.add(HexDirection.NORTH.getDirection());
        array[2] = coordinates.add(HexDirection.NORTH_WEST.getDirection());
        array[3] = coordinates.add(HexDirection.SOUTH_WEST.getDirection());
        array[4] = coordinates.add(HexDirection.SOUTH.getDirection());
        array[5] = coordinates.add(HexDirection.SOUTH_EAST.getDirection());
    }

    public HexCoordinates getNeighbor(HexDirection direction) {
        return array[direction.getIndex()];
    }

    public HexCoordinates getNeighbor(int n) {
        return array[n];
    }

    public boolean contains(HexCoordinates coordinates) {
        for (HexCoordinates neighbor : array) {
            if (neighbor == coordinates) return true;
        }
        return false;
    }
}
