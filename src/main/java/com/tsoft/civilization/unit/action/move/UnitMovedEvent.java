package com.tsoft.civilization.unit.action.move;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class UnitMovedEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap = true;
    private final boolean updateControlPanel = true;
    private final boolean updateStatusPanel = true;

    private final L10n message = L10nUnit.UNIT_MOVED_EVENT;
    private final L10n unitName;

    public Object[] getArgs() {
        return new Object[] {
            "$unitName", unitName
        };
    }
}
