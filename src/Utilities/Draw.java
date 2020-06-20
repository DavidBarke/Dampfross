package Utilities;

import java.awt.*;

public final class Draw {

    public static void centeredText(Graphics2D g, String text, float x, float y) {
        FontMetrics metrics = g.getFontMetrics();

        float xCentered = x - metrics.stringWidth(text) / (float) 2.;
        float yCentered = y - metrics.getHeight() / (float) 2. + metrics.getAscent();

        g.drawString(text, xCentered, yCentered);
    }
}
