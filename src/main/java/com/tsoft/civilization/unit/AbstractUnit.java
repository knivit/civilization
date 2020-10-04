package com.tsoft.civilization.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.event.Event;
import com.tsoft.civilization.world.World;
import lombok.EqualsAndHashCode;
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
public abstract class AbstractUnit implements HasCombatStrength, CanBeBuilt {
    private String id = UUID.randomUUID().toString();

    private Civilization civilization;

    private Point location;
    private int passScore;
    private CombatStrength combatStrength;
    private boolean isMoved;

    private final ArrayList<AbstractSkill> skills = new ArrayList<>();

    public abstract UnitCategory getUnitCategory();
    public abstract void initPassScore();
    public abstract int getGoldCost();
    public abstract AbstractUnitView getView();
    public abstract String getClassUuid();

    // Method of a unit from the catalog (they don't have civilization etc)
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    protected abstract CombatStrength getBaseCombatStrength();

    protected AbstractUnit() { }

    // Initialization on create the object
    public void init() {
        combatStrength = new CombatStrength(this, getBaseCombatStrength());
        initPassScore();
    }

    public void startYear() {
        initPassScore();
        isMoved = false;
    }

    public void move() {
        if (!isMoved) {
            doMove();
        }
    }

    private void doMove() {
        // TODO do an artificial move

        isMoved = true;
    }

    public void stopYear() {

    }

    @Override
    public String getId() {
        return id;
    }

    public World getWorld() {
        return civilization.getWorld();
    }

    @Override
    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public TilesMap getTilesMap() {
        return getWorld().getTilesMap();
    }

    public AbstractTile getTile() {
        return getTilesMap().getTile(location);
    }

    @Override
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getPassScore() {
        return passScore;
    }

    @Override
    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    @Override
    public CombatStrength getCombatStrength() {
        return combatStrength;
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public int getGoldUnitKeepingExpenses() {
        return 3;
    }

    @Override
    public UnitList<?> getUnitsAround(int radius) {
        return civilization.units().getUnitsAround(location, radius);
    }

    public void moveTo(Point location) {
        setLocation(location);

        int tilePassCost = getTilesMap().getTile(location).getPassCost(this);
        passScore -= tilePassCost;
    }

    public boolean canBeCaptured() {
        return !getUnitCategory().isMilitary();
    }

    public void capturedBy(HasCombatStrength capturer) {
        civilization.units().removeUnit(this);

        // re-create foreigner's unit ID so it can't be used anymore
        id = UUID.randomUUID().toString();

        // captured unit can't move
        setPassScore(0);
        capturer.getCivilization().units().addUnit(this, location);
    }

    @Override
    public boolean isDestroyed() {
        return getCombatStrength().isDestroyed();
    }

    @Override
    public void destroyedBy(HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        combatStrength.setDestroyed(true);

        // destroyer may be null (for settlers who had settled)
        if (destroyer != null) {
            Event event = new Event(Event.UPDATE_WORLD, destroyer, L10nUnit.UNIT_HAS_WON_ATTACK_EVENT);
            destroyer.getCivilization().addEvent(event);
        }

        // destroy all units located in that location
        if (destroyOtherUnitsAtLocation) {
            UnitList<?> units = civilization.getWorld().getUnitsAtLocation(location);
            for (AbstractUnit unit : units) {
                // to prevent a recursion
                if (!unit.equals(this)) {
                    unit.destroyedBy(destroyer, false);
                }
            }
        }

        // destroy the unit itself
        civilization.units().removeUnit(this);
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
