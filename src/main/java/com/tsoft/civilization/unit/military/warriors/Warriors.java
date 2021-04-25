package com.tsoft.civilization.unit.military.warriors;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

/**
 * Movement: 2; Strength: 6; Ranged Strength: 0; Cost: 40; Requires Resource: none
 * Technology: (none); Obsolete with: Metal Casting; Upgrades to: Swordsman (80 gold)
 *
 * Notes: You normally start the game with a Settler and one Warrior unit.
 * The Warrior has a fairly decent combat strength, and can be expected to be useful deeper
 * into the Ancient and even Classical eras than in previous games.
 */
public class Warriors extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private static final String classUuid = UUID.randomUUID().toString();

    @Getter
    private final CombatStrength baseCombatStrength = CombatStrength.builder()
        .meleeAttackStrength(10)
        .targetBackFireStrength(5)
        .defenseStrength(20)
        .canConquerCity(true)
        .build();

    @Getter
    private final WarriorsView view = new WarriorsView();

    public Warriors(Civilization civilization) {
        super(civilization);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.MILITARY_MELEE;
    }

    @Override
    public void initPassScore() {
        setPassScore(5);
    }

    @Override
    public int getProductionCost() {
        return 40;
    }

    @Override
    public int getGoldCost() {
        return 200;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }
}
