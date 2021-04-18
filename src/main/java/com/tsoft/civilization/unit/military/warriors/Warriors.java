package com.tsoft.civilization.unit.military.warriors;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

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

    private static final CombatStrength COMBAT_STRENGTH = CombatStrength.builder()
        .meleeAttackStrength(10)
        .targetBackFireStrength(5)
        .defenseStrength(20)
        .canConquerCity(true)
        .build();

    private static final WarriorsView VIEW = new WarriorsView();

    public Warriors(Civilization civilization) {
        super(civilization);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.MILITARY_MELEE;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(5);
    }

    @Override
    public WarriorsView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost() {
        return 40;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost() {
        return 200;
    }
}
