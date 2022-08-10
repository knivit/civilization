package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class CreateCityEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap = true;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCity.NEW_CITY_EVENT;
    private final L10n cityName;

    public Object[] getArgs() {
        return new Object[] {
            "$cityName", cityName
        };
    }
}
