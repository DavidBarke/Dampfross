package de.dampfross.hex.coordinates;

import java.util.HashMap;
import java.util.Map;

// Cube coordinates
public class HexLocation extends HexCoordinates {
    private final Map<HexDirection, HexCoordinates> neighbors = new HashMap<>();

    HexLocation(int x, int y, int z) {
        super(x, y, z);

        for (int i = 0; i < 6; ++i) {
            neighbors.put(HexDirection.fromIndex(i), this.add(HexDirection.fromIndex(i).getDirection()));
        }
    }

    public HexLocation(int q, int r) {
        super(q, r);

        for (int i = 0; i < 6; ++i) {
            neighbors.put(HexDirection.fromIndex(i), this.add(HexDirection.fromIndex(i).getDirection()));
        }
    }

    HexLocation(double x, double y, double z) {
        super(x, y, z);

        for (int i = 0; i < 6; ++i) {
            neighbors.put(HexDirection.fromIndex(i), this.add(HexDirection.fromIndex(i).getDirection()));
        }
    }

    public boolean isNeighbor(HexCoordinates hexCoordinates) {
        return neighbors.containsValue(hexCoordinates);
    }

    public HexCoordinates getNeighbor(HexDirection direction) {
        return neighbors.get(direction);
    }

    public Map<HexDirection, HexCoordinates> getNeighbors() {
        return neighbors;
    }
}
