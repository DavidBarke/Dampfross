package de.dampfross.hex.edge;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HexEdgeSerializer extends StdSerializer<HexEdge> {
    protected HexEdgeSerializer() {
        this(null);
    }

    protected HexEdgeSerializer(Class<HexEdge> t) {
        super(t);
    }

    @Override
    public void serialize(HexEdge hexEdge, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", hexEdge.getId());
        serializerProvider.defaultSerializeField("type", hexEdge.getEdgeType(), jsonGenerator);
        jsonGenerator.writeEndObject();
    }
}
