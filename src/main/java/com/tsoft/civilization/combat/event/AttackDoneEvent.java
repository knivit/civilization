package com.tsoft.civilization.combat.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class AttackDoneEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap = true;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel = true;

    private final L10n message = L10nUnit.ATTACK_DONE_EVENT;

    public Object[] getArgs() {
        return new Object[] { };
    }
}
