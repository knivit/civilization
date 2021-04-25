package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class UnitBoughtEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCivilization.UNIT_BOUGHT_EVENT;
    private final L10n unitName;

    public Object[] getArgs() {
        return new Object[] {
            "$unitName", unitName
        };
    }
}