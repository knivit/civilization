package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class UnitHasCapturedCityEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCity.UNIT_HAS_CAPTURED_CITY;
    private final L10n unitName;
    private final L10n cityName;

    public Object[] getArgs() {
        return new Object[] {
            "$unitName", unitName,
            "$cityName", cityName
        };
    }
}
