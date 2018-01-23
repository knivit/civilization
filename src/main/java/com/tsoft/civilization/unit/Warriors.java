package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.WarriorsView;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 6; Ranged Strength: 0; Cost: 40; Requires Resource: none
 * Technology: (none); Obsolete with: Metal Casting; Upgrades to: Swordsman (80 gold)
 *
 * Notes: You normally start the game with a Settler and one Warrior unit.
 * The Warrior has a fairly decent combat strength, and can be expected to be useful deeper
 * into the Ancient and even Classical eras than in previous games.
 */
public class Warriors extends AbstractUnit<WarriorsView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final Warriors INSTANCE = new Warriors();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength(10, 5, 20, 0, 0, true);
    private static final WarriorsView VIEW = new WarriorsView();

    @Override
    public UnitType getUnitType() {
        return UnitType.WARRIORS;
    }

    @Override
    public UnitKind getUnitKind() {
        return UnitKind.MILITARY_MELEE;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
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
