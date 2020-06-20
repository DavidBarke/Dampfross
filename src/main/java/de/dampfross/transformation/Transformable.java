package de.dampfross.transformation;

public interface Transformable {

    public abstract void translate(boolean left, boolean up, boolean right, boolean down);
    public abstract void rotate(boolean positive, boolean negative);
    public abstract void scale(double scaleFactor);
}
