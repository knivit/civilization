package com.tsoft.civilization.improvement.city.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class TileBoughtEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCity.TILE_BOUGHT_EVENT;
    private final L10n cityName;
    private final int gold;

    public Object[] getArgs() {
        return new Object[] {
            "$cityName", cityName,
            "$gold", gold
        };
    }
}