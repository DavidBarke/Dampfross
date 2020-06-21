package de.dampfross.hex.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.dampfross.hex.coordinates.HexDirection;

import java.io.IOException;

public class HexEntitySerializer extends StdSerializer<HexEntity> {

    public HexEntitySerializer() {
        this(null);
    }

    protected HexEntitySerializer(Class<HexEntity> t) {
        super(t);
    }

    @Override
    public void serialize(HexEntity hexEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        serializerProvider.defaultSerializeField("location", hexEntity.location, jsonGenerator);
        jsonGenerator.writeArrayFieldStart("edges");
        for (int i = 0; i < 6; ++i) {
            jsonGenerator.writeNumber(hexEntity.getEdgeIds()[i]);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
