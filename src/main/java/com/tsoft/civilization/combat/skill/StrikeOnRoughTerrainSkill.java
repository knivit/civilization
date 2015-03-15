package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.util.Point;

public class StrikeOnRoughTerrainSkill extends AbstractSkill {
    @Override
    public int getAttackStrikePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        Point attackerLocation = attacker.getLocation();
        AbstractTile tile = attacker.getCivilization().getTilesMap().getTile(attackerLocation);
        TileType tileType = tile.getTileType();

        int value = 0;
        if (TileType.EARTH_ROUGH.equals(tileType)) {
            value += 15;
        }
        return value;
    }

    @Override
    public int getTargetStrikeOnDefensePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        return 0;
    }
}
