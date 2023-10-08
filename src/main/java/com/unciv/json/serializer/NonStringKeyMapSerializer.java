package com.unciv.json.serializer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class NonStringKeyMapSerializer<K, V> implements Json.Serializer<Map<K, V>> {

    private final K keyClass;

    @Override
    public void write(Json json, Map<K, V> obj, Class aClass) {
        json.writeObjectStart();
        json.writeType(aClass);
        json.writeArrayStart("entries");
        for (Map.Entry<K, V> entry : obj.entrySet()) {
            json.writeArrayStart();
            json.writeValue(entry.getKey());
            json.writeValue(entry.getValue(), null);
            json.writeArrayEnd();
        }
        json.writeArrayEnd();
        json.writeObjectEnd();
    }

    @Override
    public Map<K, V> read(Json json, JsonValue jsonValue, Class aClass) {
        JsonValue entries = jsonValue.get("entries");
        return readNewFormat(entries, json);
    }

    private Map<K, V> readNewFormat(JsonValue entries, Json json) {
        Map<K, V> result = new HashMap<>();
        JsonValue entry = entries.child;

        while (entry != null) {
            K key = (K)json.readValue(keyClass.getClass(), entry.child);
            V value = json.readValue(null, entry.child.next);

            result.put(key, value);
            entry = entry.next;
        }

        return result;
    }
}
