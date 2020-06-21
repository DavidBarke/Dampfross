package de.dampfross.hex.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.dampfross.Builder;
import de.dampfross.hex.coordinates.HexCoordinateSystem;
import de.dampfross.hex.coordinates.HexLocation;

@JsonDeserialize(using = HexEntityBuilderDeserializer.class)
public class HexEntityBuilder implements Builder<HexEntity> {
    private HexCoordinateSystem hexCoordinateSystem;
    private HexLocation hexLocation;
    private int[] edgeIds;

    public HexEntityBuilder(HexLocation hexLocation, int[] edgeIds) {
        this.hexLocation = hexLocation;
        this.edgeIds = edgeIds;
    }

    public void setHexCoordinateSystem(HexCoordinateSystem hexCoordinateSystem) {
        this.hexCoordinateSystem = hexCoordinateSystem;
    }

    @Override
    public HexEntity build() {
        return new HexEntity(hexCoordinateSystem, hexLocation, edgeIds);
    }
}
