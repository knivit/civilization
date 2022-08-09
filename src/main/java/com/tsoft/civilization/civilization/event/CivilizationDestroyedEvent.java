package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class CivilizationDestroyedEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap = true;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nWorld.CIVILIZATION_DESTROYED_EVENT;
    private final L10n civilizationName;

    public Object[] getArgs() {
        return new Object[] {
            "$civilizationName", civilizationName
        };
    }
}
