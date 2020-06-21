package de.dampfross.hex.edge;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.dampfross.hex.coordinates.HexDirection;
import de.dampfross.hex.entity.HexEntity;

@JsonDeserialize(using = HexEdgeBuilderDeserializer.class)
public class HexEdgeBuilder {
    private final int id;

    private HexEntity e1;
    private HexEntity e2;

    private HexDirection direction;

    private HexEdgeType type;

    public HexEdgeBuilder(int id, HexEdgeType type) {
        this.id = id;
        this.type = type;
    }

    public void addHexEntity(HexEntity hexEntity, HexDirection direction) {
        if (hexEntity == null) throw new NullPointerException();

        if (e1 == null) {
            e1 = hexEntity;
            this.direction = direction;
        } else {
            e2 = hexEntity;
        }
    }

    public HexEdge toHexEdge() {
        return new HexEdge(id, e1, e2, direction);
    }

    public int getId() {
        return id;
    }
}
