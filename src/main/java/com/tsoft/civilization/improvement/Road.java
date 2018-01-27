package com.tsoft.civilization.improvement;

import com.tsoft.civilization.L10n.L10nImprovement;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.world.economic.ImprovementScore;
import com.tsoft.civilization.world.economic.ImprovementSupply;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.improvement.AbstractImprovementView;
import com.tsoft.civilization.web.view.improvement.RoadView;
import com.tsoft.civilization.world.Civilization;

public class Road extends AbstractImprovement {
    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(10);

    private static final AbstractImprovementView VIEW = new RoadView();

    public Road(Civilization civilization, Point location) {
        super(civilization, location);
    }

    @Override
    public ImprovementScore getSupply() {
        ImprovementScore score = new ImprovementScore(null);
        score.add(new ImprovementSupply(0, 0, -2, 0), L10nImprovement.IMPROVEMENT_EXPENSES_SUPPLY);
        return score;
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
