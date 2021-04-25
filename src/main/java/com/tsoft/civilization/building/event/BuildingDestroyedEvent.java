package com.tsoft.civilization.building.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.L10nBuilding;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class BuildingDestroyedEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nBuilding.BUILDING_DESTROYED;
    private final L10n buildingName;

    public Object[] getArgs() {
        return new Object[] {
            "$buildingName", buildingName
        };
    }
}
