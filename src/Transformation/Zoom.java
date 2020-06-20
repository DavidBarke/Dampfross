package Transformation;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Zoom implements MouseWheelListener {
    Transformable transformable;

    public Zoom(Transformable transformable) {
        this.transformable = transformable;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        transformable.scale(e.getPreciseWheelRotation());
    }
}
