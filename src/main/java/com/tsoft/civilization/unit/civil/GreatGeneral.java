package com.tsoft.civilization.unit.civil;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.web.view.unit.GreatGeneralView;
import com.tsoft.civilization.civilization.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Great Generals are produced when a civilization has repeated victories in combat.
 * His presence gives +25% combat bonus to friendly units within 2 tiles. He can sacrifice
 * himself to create a Citadel (a super-Fort), or to trigger a Golden Age. The Citadel can
 * only be built in your own territory (unlike a regular Fort, which can be built in unclaimed territory.
 */
public class GreatGeneral extends AbstractUnit<GreatGeneralView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final GreatGeneral STUB = new GreatGeneral();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(0);

    private static final GreatGeneralView VIEW = new GreatGeneralView();

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
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
    public GreatGeneralView getView() {
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
