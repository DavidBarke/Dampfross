package de.dampfross.hex.edge;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class HexEdgeBuilderDeserializer extends StdDeserializer<HexEdgeBuilder> {
    protected HexEdgeBuilderDeserializer() {
        this(null);
    }

    protected HexEdgeBuilderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HexEdgeBuilder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode entryNode = objectMapper.readTree(jsonParser);

        int id = entryNode.get("id").intValue();
        HexEdgeType type = HexEdgeType.valueOf(entryNode.get("type").textValue());

        return new HexEdgeBuilder(id, type);
    }
}
