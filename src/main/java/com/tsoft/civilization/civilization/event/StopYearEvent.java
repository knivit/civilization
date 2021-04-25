package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class StopYearEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel = true;
    private final boolean updateStatusPanel;

    private final L10n message = L10nWorld.MOVE_DONE_EVENT;
    private final L10n civilizationName;

    public Object[] getArgs() {
        return new Object[] {
            "$civilizationName", civilizationName
        };
    }
}
