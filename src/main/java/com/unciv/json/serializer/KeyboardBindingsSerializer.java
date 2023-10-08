package com.unciv.json.serializer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.unciv.ui.components.input.KeyCharAndCode;
import com.unciv.ui.components.input.KeyboardBinding;
import com.unciv.ui.components.input.KeyboardBindings;

import java.util.Map;

public class KeyboardBindingsSerializer implements Json.Serializer<KeyboardBindings> {

    @Override
    public void write(Json json, KeyboardBindings obj, Class aClass) {
        json.writeObjectStart();
        for (Map.Entry<KeyboardBinding, KeyCharAndCode> entry : obj.entrySet()) {
            json.writeValue(entry.getKey().name(), entry.getValue(), KeyCharAndCode.class);
        }
        json.writeObjectEnd();
    }

    @Override
    public KeyboardBindings read(Json json, JsonValue jsonData, Class type) {
        KeyboardBindings bindings = new KeyboardBindings();
        if (jsonData != null && jsonData.notEmpty()) {
            for (JsonValue element : jsonData) {
                bindings.put(element.name, element.asString());
            }
        }
        return bindings;
    }
}
