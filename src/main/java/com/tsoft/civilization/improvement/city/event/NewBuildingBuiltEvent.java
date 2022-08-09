package com.tsoft.civilization.improvement.city.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class NewBuildingBuiltEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCity.NEW_BUILDING_BUILT_EVENT;
    private final L10n buildingName;

    public Object[] getArgs() {
        return new Object[] {
            "$buildingName", buildingName
        };
    }
}
