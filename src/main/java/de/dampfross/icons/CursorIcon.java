package de.dampfross.icons;

import javax.swing.*;
import java.awt.*;

public class CursorIcon extends ImageIcon {
    private static final String file = "resources/img/mouseCursor.png";
    private int width = 22;
    private int height = 19;

    public CursorIcon() {
        super(file);
        //resize(width, height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        //resize(width, height);
    }

    private void resize(int width, int height) {
        Image image = getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setImage(resizedImage);
    }

    @Override
    public int getIconWidth() {
        return this.width;
    }

    @Override
    public int getIconHeight() {
        return this.height;
    }
}
