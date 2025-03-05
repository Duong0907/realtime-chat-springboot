package com.example.demo.utils;

import com.example.demo.dto.websocket.WebSocketMessageRequest;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Component
public class JSONUtil {
    private final Gson gson;

    public JSONUtil() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new CustomInstantSerializer())
                .create();
    }

    public String objectToJSON(Object obj) {
        return gson.toJson(obj);
    }

    public WebSocketMessageRequest jsonToWebSocketMessageRequest(String json) {
        return gson.fromJson(json, WebSocketMessageRequest.class);
    }

}

class CustomInstantSerializer implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Instant.parse(json.getAsString());
    }
}