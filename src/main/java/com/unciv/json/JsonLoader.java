package com.unciv.json;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.unciv.json.serializer.DurationSerializer;
import com.unciv.json.serializer.KeyCharAndCodeSerializer;
import com.unciv.json.serializer.KeyboardBindingsSerializer;
import com.unciv.json.serializer.NonStringKeyMapSerializer;
import com.unciv.json.HashMapVector2;
import com.unciv.logic.map.tile.TileHistory;
import com.unciv.ui.components.input.KeyCharAndCode;
import com.unciv.ui.components.input.KeyboardBindings;

import java.time.Duration;

public class JsonLoader {

    private static Json create() {
        Json json = new Json(JsonWriter.OutputType.json);

        // Gdx default output type is JsonWriter.OutputType.minimal, which generates invalid Json - e.g. most quotes removed.
        // The constructor parameter above changes that to valid Json
        // Note an instance set to json can read minimal and vice versa

        json.setIgnoreDeprecated(true);
        json.setIgnoreUnknownFields(true);

        json.setSerializer(HashMapVector2.class, new NonStringKeyMapSerializer(Vector2.class));
        json.setSerializer(Duration.class, new DurationSerializer());
        json.setSerializer(KeyCharAndCode.class, new KeyCharAndCodeSerializer());
        json.setSerializer(KeyboardBindings.class, new KeyboardBindingsSerializer());
        json.setSerializer(TileHistory.class, new TileHistorySerializer());
        json.setSerializer(CivRankingHistory.class, new CivRankingHistorySerializer());
        json.setSerializer(Notification.class, new  NotificationSerializer());
    }
}
