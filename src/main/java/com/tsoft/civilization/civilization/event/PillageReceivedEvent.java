package com.tsoft.civilization.civilization.event;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class PillageReceivedEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCivilization.PILLAGE_RECEIVED;
    private final L10n targetName;
    private final Supply receivedSupply;

    public Object[] getArgs() {
        return new Object[] {
            "$targetName", targetName,
            "$receivedSupply", receivedSupply
        };
    }
}
