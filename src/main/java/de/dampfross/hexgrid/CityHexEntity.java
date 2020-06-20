package de.dampfross.hexgrid;

import de.dampfross.utilities.Draw;

import java.awt.*;

public class CityHexEntity extends HexEntity {
    int cityNumber;

    CityHexEntity(HexCoordinateSystem coordinateSystem, HexLocation location, int cityNumber) {
        super(coordinateSystem, location);
        this.cityNumber = cityNumber;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        float x = (float) innerHexagon.center.x;
        float y = (float) innerHexagon.center.y;

        Draw.centeredText(g, String.valueOf(cityNumber), x, y);
    }
}
