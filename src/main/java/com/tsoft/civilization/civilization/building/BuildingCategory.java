package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BuildingCategory {

    BUILDING(L10nBuilding.BUILDING_CATEGORY),
    NATIONAL_WONDER(L10nBuilding.NATIONAL_WONDER_CATEGORY),
    WORLD_WONDER(L10nBuilding.WORLD_WONDER_CATEGORY);

    @Getter
    private final L10n localizedName;
}
