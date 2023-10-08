package com.unciv.logic.map.tile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CityCenterType {

    None("N"),
    Regular("R"),
    Capital("C");

    private final String serializedRepresentation;

    public static CityCenterType deserialize(String text) {
        for (CityCenterType type : CityCenterType.values()) {
            if (type.serializedRepresentation.equals(text)) {
                return type;
            }
        }
        return None;
    }
}
