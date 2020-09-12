package com.tsoft.civilization.improvement.road;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;

public class Road extends AbstractImprovement {
    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(10);

    private static final AbstractImprovementView VIEW = new RoadView();

    public Road(Civilization civilization, Point location) {
        super(civilization, location);
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().gold(-2).build();
    }

    @Override
    public AbstractImprovementView getView() {
        return VIEW;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }
}
