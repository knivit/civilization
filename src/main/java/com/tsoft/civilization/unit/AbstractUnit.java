package com.tsoft.civilization.unit;

import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;
import com.tsoft.civilization.combat.service.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.city.construction.CanBeBuilt;
import com.tsoft.civilization.improvement.city.construction.CityConstructionService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.DifficultyLevel;
import com.tsoft.civilization.world.HasHistory;
import com.tsoft.civilization.world.World;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * - Only one unit of each category (Military, Naval or Civilian) is allowed per hex.
 * - Air units are an exception to the one unit per hex rule. Aircraft and missiles can stack in a city (no limit) or on a Carrier (up to 3).
 *   Missiles can also stack on a Missile Cruiser (up to 3) or on a Nuclear Submarine (up to 2).
 * - Ranged units (including ships) can only used ranged attacks, and cannot melee attack (though they will defend if attacked).
 * - Land units can now embark on water with the Embarkation promotion, attained with Optics.
 * - Military land units exert a 1-hex zone of control, which reduces adjacent enemy movement to 1 hex per turn.
 *   "If you move a unit from one tile adjacent to an enemy to another tile adjacent to that same enemy it uses up your turn,
 *   no matter how many movement points you have." - Jon Shafer
 * - Siege units have a bonus attacking cities, but most must "set up" to fire first, and so can't move and fire in the same turn.
 * - In addition to any resources they require, units also require maintenance in gold.
 * - There is now a hard cap on the number of units your empire can support. It is calculated as (Base Supply + Number of Cities + Number of Citizens).
 *   For Normal difficulty, Base Supply appears to be 5. So this number is fairly generous;
 *   the only time you will run up against this cap is when trying to build a large early army with only a single city.
 * - Decommissioning units now refunds some gold, if done inside your borders.
 * - All units have 100 hit points.
 */
@Slf4j
@EqualsAndHashCode(of = "id")
public abstract class AbstractUnit implements HasId, HasView, HasCombatStrength, HasHistory, CanBeBuilt {
    @Getter @Setter
    private String id = UUID.randomUUID().toString();

    @Getter @Setter
    private Civilization civilization;

    @Getter @Setter
    private Point location;

    @Getter @Setter
    private int passScore;

    @Getter @Setter
    private CombatStrength combatStrength;

    private boolean isMoved;

    private final ArrayList<AbstractSkill> skills = new ArrayList<>();

    public abstract String getClassUuid();
    public abstract UnitCategory getUnitCategory();
    public abstract void initPassScore();
    public abstract int getGoldCost(Civilization civilization);
    public abstract int getBaseProductionCost();

    public abstract AbstractUnitView getView();
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    protected AbstractUnit(Civilization civilization) {
        this.civilization = civilization;
    }

    // Initialization on create the object
    public void init() {
        combatStrength = getBaseCombatStrength();
        initPassScore();
    }

    @Override
    public void startYear() {
        initPassScore();
        isMoved = false;
    }

    @Override
    public void stopYear() {
    }

    public World getWorld() {
        return civilization.getWorld();
    }

    public TilesMap getTilesMap() {
        return getWorld().getTilesMap();
    }

    public AbstractTile getTile() {
        return getTilesMap().getTile(location);
    }

    @Override
    public CombatStrength calcCombatStrength() {
        return combatStrength;
    }

    @Override
    public int getProductionCost(Civilization civilization) {
        DifficultyLevel difficultyLevel = civilization.getWorld().getDifficultyLevel();
        int baseProductionCost = getBaseProductionCost();
        return (int)Math.round(baseProductionCost * CityConstructionService.UNIT_COST_PER_DIFFICULTY_LEVEL.get(difficultyLevel));
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public int getGoldUnitKeepingExpenses() {
        return 3;
    }

    public boolean canBeCaptured() {
        return !getUnitCategory().isMilitary();
    }

    @Override
    public boolean isDestroyed() {
        return getCombatStrength().isDestroyed();
    }

    public void destroy() {
        combatStrength = combatStrength.copy()
            .isDestroyed(true)
            .build();
    }

    public boolean isActionAvailable() {
        return !isDestroyed() && (getPassScore() > 0);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "civilization=" + civilization +
            ", location=" + location +
            ", passScore=" + passScore +
            ", combatStrength=" + combatStrength +
        "}";
    }
}
