package com.tsoft.civilization.combat.event;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class UnitHasWonAttackEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap = true;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nUnit.UNIT_HAS_WON_ATTACK_EVENT;
    private final L10n attackerName;
    private final L10n targetName;

    public Object[] getArgs() {
        return new Object[] {
            "$attackerName", attackerName,
            "$targetName", targetName
        };
    }
}
