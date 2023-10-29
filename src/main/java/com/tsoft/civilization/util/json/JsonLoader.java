package com.tsoft.civilization.util.json;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tsoft.civilization.util.json.serializer.DurationSerializer;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {

    public static <T> List<T> loadFromFile(Class<T> clazz, String jsonFileName) {
        try {
            URI fileName = JsonLoader.class.getResource(jsonFileName).toURI();
            String json = Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
            return create().fromJson(ArrayList.class, clazz, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Json create() {
        Json json = new Json(JsonWriter.OutputType.json);

        // Gdx default output type is JsonWriter.OutputType.minimal, which generates invalid Json - e.g. most quotes removed.
        // The constructor parameter above changes that to valid Json
        // Note an instance set to json can read minimal and vice versa

        json.setIgnoreDeprecated(true);
        json.setIgnoreUnknownFields(true);

        json.setSerializer(Duration.class, new DurationSerializer());
        return json;
    }
}
