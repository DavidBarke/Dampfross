package de.dampfross.hex.coordinates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HexCoordinatesSerializer extends StdSerializer<HexCoordinates> {
    protected HexCoordinatesSerializer() {
        this(null);
    }

    protected HexCoordinatesSerializer(Class<HexCoordinates> t) {
        super(t);
    }

    @Override
    public void serialize(HexCoordinates hexCoordinates, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("q", hexCoordinates.q);
        jsonGenerator.writeNumberField("r", hexCoordinates.r);
        jsonGenerator.writeEndObject();
    }
}
