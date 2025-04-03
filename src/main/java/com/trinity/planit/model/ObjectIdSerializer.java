package com.trinity.planit.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerializer extends JsonSerializer {


    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value instanceof ObjectId) {
            gen.writeString(((ObjectId) value).toHexString()); // Convert ObjectId to String
        } else {
            gen.writeNull();  // Handle case where the value is not an ObjectId
        }
    }
}
