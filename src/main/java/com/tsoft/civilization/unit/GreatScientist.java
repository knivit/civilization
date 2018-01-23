package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.GreatScientistView;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Can construct the Academy improvement, or discover a Technology, or trigger a Golden Age.
 * All these consume the unit. The technology can be chosen from a list of any technologies
 * that you could normally research at that point.
 */
public class GreatScientist extends AbstractUnit<GreatScientistView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final GreatScientist INSTANCE = new GreatScientist();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength(0, 0, 20, 0, 0, false);
    private static final GreatScientistView VIEW = new GreatScientistView();

    @Override
    public UnitType getUnitType() {
        return UnitType.GREAT_SCIENTIST;
    }

    @Override
    public UnitKind getUnitKind() {
        return UnitKind.CIVIL;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(2);
    }

    @Override
    public GreatScientistView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost() {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost() {
        return -1;
    }
}
