package com.tsoft.civilization.world.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.Event;
import com.tsoft.civilization.world.L10nWorld;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class NewEraStartedEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel = true;
    private final boolean updateStatusPanel;

    private final L10n message = L10nWorld.WORLD_START_ERA_EVENT;
    private final int era;

    public Object[] getArgs() {
        return new Object[] {
            "$era", era
        };
    }
}
