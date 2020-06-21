package de.dampfross.hex.map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.dampfross.hex.coordinates.HexCoordinateSystem;
import de.dampfross.hex.coordinates.HexCoordinates;
import de.dampfross.hex.coordinates.HexDirection;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.edge.HexEdgeBuilder;
import de.dampfross.hex.entity.HexEntity;
import de.dampfross.hex.entity.HexEntityBuilder;

import java.io.IOException;
import java.util.*;

public class HexMapDeserializer extends StdDeserializer<HexMap> {
    protected HexMapDeserializer() {
        this(null);
    }

    protected HexMapDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HexMap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode entryNode = objectMapper.readTree(jsonParser);

        // Build coordinate system
        int size = entryNode.get("coordinateSystem").get("size").intValue();
        HexCoordinateSystem coordinateSystem = new HexCoordinateSystem(size);

        // Build HexEntity instances
        JsonNode entitiesParentNode = entryNode.get("entities");
        Set<HexEntity> hexEntities = new HashSet<>();
        for (Iterator<JsonNode> it = entitiesParentNode.elements(); it.hasNext(); ) {
            JsonNode entityNode = it.next();

            HexEntityBuilder entityBuilder = objectMapper.treeToValue(entityNode, HexEntityBuilder.class);
            entityBuilder.setHexCoordinateSystem(coordinateSystem);
            hexEntities.add(entityBuilder.build());
        }

        // Create HexEdgeBuilder instances, that are later used to build HexEdge instances
        JsonNode edgesParentNode = entryNode.get("edges");
        Map<Integer, HexEdgeBuilder> hexEdgeBuilderMap = new HashMap<>();
        for (Iterator<JsonNode> it = edgesParentNode.elements(); it.hasNext(); ) {
            JsonNode edgeNode = it.next();

            HexEdgeBuilder edgeBuilder = objectMapper.treeToValue(edgeNode, HexEdgeBuilder.class);
            hexEdgeBuilderMap.put(edgeBuilder.getId(), edgeBuilder);
        }

        // Add entities to edge builders
        for (HexEntity hexEntity : hexEntities) {
            int[] edgeIds = hexEntity.getEdgeIds();
            for (int i = 0; i < 6; ++i) {
                hexEdgeBuilderMap.get(edgeIds[i]).addHexEntity(hexEntity, HexDirection.fromIndex(i));
            }
        }

        // Build entityMap
        Map<HexCoordinates, HexEntity> entityMap = new HashMap<>();
        for (HexEntity hexEntity : hexEntities) {
            entityMap.put(hexEntity.location, hexEntity);
        }

        // Build edgesMap
        Map<Integer, HexEdge> edgesMap = new HashMap<>();
        for (HexEdgeBuilder hexEdgeBuilder : hexEdgeBuilderMap.values()) {
            edgesMap.put(hexEdgeBuilder.getId(), hexEdgeBuilder.toHexEdge());
        }

        // Build HexMap instance
        return new HexMap(entityMap, edgesMap, coordinateSystem);
    }
}
