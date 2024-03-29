package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Climate {

    COLD(L10nWorld.CLIMATE_COLD),
    NORMAL(L10nWorld.CLIMATE_NORMAL),
    HOT(L10nWorld.CLIMATE_HOT);

    @Getter
    private final L10n l10n;

    public static Climate find(String name) {
        return Arrays.stream(Climate.values())
            .filter(e -> e.name().equalsIgnoreCase(name))
            .findAny()
            .orElse(null);
    }
}
