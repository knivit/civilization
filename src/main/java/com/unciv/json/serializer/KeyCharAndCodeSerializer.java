package com.unciv.json.serializer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.unciv.ui.components.input.KeyCharAndCode;

public class KeyCharAndCodeSerializer implements Json.Serializer<KeyCharAndCode> {

    @Override
    public void write(Json json, KeyCharAndCode obj, Class aClass) {
        json.writeValue(obj, String.class, null);
    }

    @Override
    public KeyCharAndCode read(Json json, JsonValue jsonData, Class type) {
        return KeyCharAndCode.parse(jsonData.asString());
    }
}
