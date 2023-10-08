package com.unciv.json.serializer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.time.Duration;

public class DurationSerializer implements Json.Serializer<Duration> {

    @Override
    public void write(Json json, Duration obj, Class aClass) {
        json.writeValue(obj.toString());
    }

    @Override
    public Duration read(Json json, JsonValue jsonValue, Class aClass) {
        return Duration.parse(jsonValue.asString());
    }
}
