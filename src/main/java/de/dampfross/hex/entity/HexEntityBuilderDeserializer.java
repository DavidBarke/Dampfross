package de.dampfross.hex.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.dampfross.hex.coordinates.HexLocation;

import java.io.IOException;
import java.util.Iterator;

public class HexEntityBuilderDeserializer extends StdDeserializer<HexEntityBuilder> {
    protected HexEntityBuilderDeserializer() {
        this(null);
    }

    protected HexEntityBuilderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HexEntityBuilder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode entryNode = objectMapper.readTree(jsonParser);

        int q = entryNode.get("location").get("q").intValue();
        int r = entryNode.get("location").get("r").intValue();

        JsonNode edgesNode = entryNode.get("edges");
        int[] edgesInt = new int[6];
        int i = 0;
        for (Iterator<JsonNode> it = edgesNode.elements(); it.hasNext(); ++i) {
            JsonNode node = it.next();
            edgesInt[i] = node.intValue();
        }

        HexLocation location = new HexLocation(q, r);

        return new HexEntityBuilder(location, edgesInt);
    }
}
