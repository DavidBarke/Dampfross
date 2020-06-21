package de.dampfross.hex.map;

public class HexMapEdgeCounter {
    private int count = 0;

    public int next() {
        return count++;
    }
}
