package com.tsoft.civilization.world.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.world.Event;
import com.tsoft.civilization.world.L10nWorld;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class DeclareFriendsEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel = true;
    private final boolean updateStatusPanel = true;

    private final L10n message = L10nWorld.DECLARE_FRIENDS_EVENT;
    private final L10n civilizationName1;
    private final L10n civilizationName2;

    public Object[] getArgs() {
        return new Object[] {
            "$civilizationName1", civilizationName1,
            "$civilizationName2", civilizationName2
        };
    }
}
