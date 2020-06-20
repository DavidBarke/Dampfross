package de.dampfross.transformation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Translation implements KeyListener {
    boolean left = false;
    boolean up = false;
    boolean right = false;
    boolean down = false;

    Transformable transformable;

    public Translation(Transformable transformable) {
        this.transformable = transformable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }

        transformable.translate(left, up, right, down);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }

        transformable.translate(left, up, right, down);
    }
}
