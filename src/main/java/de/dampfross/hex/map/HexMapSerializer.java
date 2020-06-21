package de.dampfross.hex.map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;

import java.io.IOException;

public class HexMapSerializer extends StdSerializer<HexMap> {
    protected HexMapSerializer() {
        this(null);
    }

    protected HexMapSerializer(Class<HexMap> t) {
        super(t);
    }

    @Override
    public void serialize(HexMap hexMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        serializerProvider.defaultSerializeField("coordinateSystem", hexMap.getCoordinateSystem(), jsonGenerator);
        jsonGenerator.writeArrayFieldStart("edges");
        for (HexEdge hexEdge : hexMap.hexEdgeMap.values()) {
            serializerProvider.defaultSerializeValue(hexEdge, jsonGenerator);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("entities");
        for (HexEntity hexEntity : hexMap.entityMap.values()) {
            serializerProvider.defaultSerializeValue(hexEntity, jsonGenerator);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
