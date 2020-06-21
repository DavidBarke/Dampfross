package de.dampfross.hex.coordinates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HexCoordinateSystemSerializer extends StdSerializer<HexCoordinateSystem> {

    protected HexCoordinateSystemSerializer() {
        this(null);
    }

    protected HexCoordinateSystemSerializer(Class<HexCoordinateSystem> t) {
        super(t);
    }

    @Override
    public void serialize(HexCoordinateSystem hexCoordinateSystem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("size", hexCoordinateSystem.size);
        jsonGenerator.writeEndObject();
    }
}
