package de.dampfross.hexgrid;

public class HexCoordinates implements Comparable {
    public int q;
    public int r;

    // From cube coordinates
    HexCoordinates(int x, int y, int z) {
        this(x, z);
    }

    // From fractional cube coordinates
    public HexCoordinates(double x, double y, double z) {
        double rx = Math.round(x);
        double ry = Math.round(y);
        double rz = Math.round(z);

        double xDiff = Math.abs(rx - x);
        double yDiff = Math.abs(ry - y);
        double zDiff = Math.abs(rz - z);

        if (xDiff > yDiff && xDiff > zDiff) {
            rx = - ry - rz;
        } else if (yDiff > zDiff) {
            // Do nothing
        } else {
            rz = - rx - ry;
        }

        this.q = (int) rx;
        this.r = (int) rz;
    }

    // From axial coordinates
    HexCoordinates(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int abs() {
        return Math.abs(q) + Math.abs(r);
    }

    public HexCoordinates add(HexCoordinates c) {
        return new HexCoordinates(q + c.q, r + c.r);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof HexCoordinates)) return false;

        HexCoordinates c = (HexCoordinates) o;

        return c.q == this.q && c.r == this.r;
    }

    @Override
    public int hashCode() {
        return abs();
    }

    @Override
    public int compareTo(Object o) throws ClassCastException {
        if (o == null) throw new NullPointerException();

        HexCoordinates c;
        try {
            c = (HexCoordinates) o;
        } catch (ClassCastException e) {
            throw e;
        }

        return Integer.compare(this.abs(), c.abs());
    }

    @Override
    public String toString() {
        return "HexCoordinates(" + q + ", " + r + ")";
    }

    public static double[] axialToCube(double q, double r) {
        double[] cube = new double[3];

        cube[0] = q;
        cube[1] = - q - r;
        cube[2] = r;

        return cube;
    }
}
