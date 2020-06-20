package de.dampfross.transformation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Rotation implements KeyListener {
    boolean rotatePositive;
    boolean rotateNegative;

    Transformable transformable;

    public Rotation(Transformable transformable) {
        this.transformable = transformable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'q') {
            rotatePositive = true;
        }

        if (e.getKeyChar() == 'w') {
            rotateNegative = true;
        }

        transformable.rotate(rotatePositive, rotateNegative);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'q') {
            rotatePositive = false;
        }

        if (e.getKeyChar() == 'w') {
            rotateNegative = false;
        }

        transformable.rotate(rotatePositive, rotateNegative);
    }
}
