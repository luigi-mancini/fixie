package FixieGui;

import java.awt.Color;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;

import java.io.IOException;

/**
 * Created by lmancini on 1/14/18.
 */
public class JacksonColor
{
    public static class ColorSerializer extends JsonSerializer<Color> {
        @Override
        public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeFieldName("rgba");
            gen.writeString(Integer.toHexString(value.getRGB()));
            gen.writeEndObject();
        }
    }

    public static class ColorDeserializer extends JsonDeserializer<Color> {
        @Override
        public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            TreeNode root = p.getCodec().readTree(p);
            TextNode rgba = (TextNode) root.get("rgba");
            return new Color(Integer.parseUnsignedInt(rgba.textValue(), 16), true);
        }
    }
}

