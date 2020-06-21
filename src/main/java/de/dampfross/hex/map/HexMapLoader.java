package de.dampfross.hex.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dampfross.hex.coordinates.HexCoordinates;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HexMapLoader {
    private final HexMapController hexMapController;

    public HexMapLoader(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    public void loadFromJSON(File json) {
        Map<HexCoordinates, HexEntity> entityMap = new HashMap<>();
        Set<HexEdge> edges = new HashSet<>();

        HexMap hexMap = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            hexMap = objectMapper.readValue(json, HexMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hexMap == null) hexMap = hexMapController.getHexMap();

        hexMapController.setHexMap(hexMap);
    }

    public void loadEmpty() {
        hexMapController.setHexMap(new HexMap());
    }
}
