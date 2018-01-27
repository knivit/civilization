package com.tsoft.civilization.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.unit.AbstractUnitView;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.World;

import java.util.*;

public abstract class AbstractUnit<V extends AbstractUnitView> implements HasCombatStrength, CanBeBuilt {
    private String id;
    private Civilization civilization;

    // unit's location
    private Point location;

    // a passing ability of a unit
    private int passScore;

    // unit's current strength
    private CombatStrength combatStrength;

    private ArrayList<AbstractSkill> skills = new ArrayList<>();

    public abstract UnitType getUnitType();
    public abstract UnitCategory getUnitCategory();
    public abstract void initPassScore();
    public abstract int getGoldCost();
    public abstract V getView();
    public abstract String getClassUuid();

    // Method of a unit from the catalog (they don't have civilization etc)
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    protected abstract CombatStrength getBaseCombatStrength();

    protected AbstractUnit() { }

    // Initialization on create the object
    public void init(Civilization civilization, Point location) {
        this.civilization = civilization;
        this.location = location;

        id = UUID.randomUUID().toString();
        civilization.addUnit(this);

        combatStrength = new CombatStrength(this, getBaseCombatStrength());
        initPassScore();
    }

    public void step() {
        initPassScore();
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
    public UnitCollection getUnitsAround(int radius) {
        return civilization.getUnitsAround(location, radius);
    }

    public void moveTo(Point location) {
        setLocation(location);

        int tilePassCost = getTilesMap().getTile(location).getPassCost(this);
        passScore -= tilePassCost;
    }

    public boolean canBeCaptured() {
        return !getUnitCategory().isMilitary();
    }

    public void captureBy(HasCombatStrength capturer) {
        civilization.removeUnit(this);

        // re-create foreign's unit ID so it can't be used anymore
        id = UUID.randomUUID().toString();

        // captured unit can't move
        setPassScore(0);
        capturer.getCivilization().addUnit(this);
    }

    @Override
    public boolean isDestroyed() {
        return getCombatStrength().isDestroyed();
    }

    @Override
    public void destroyBy(HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        combatStrength.setDestroyed(true);

        // destroyer may be null (for settlers who had settled)
        if (destroyer != null) {
            destroyer.getCivilization().addEvent(new Event(destroyer, L10nUnit.UNIT_HAS_WON_ATTACK_EVENT, Event.UPDATE_WORLD));
        }

        // destroy all units located in that location
        if (destroyOtherUnitsAtLocation) {
            UnitCollection units = civilization.getWorld().getUnitsAtLocation(location);
            for (AbstractUnit unit : units) {
                // to prevent a recursion
                if (!unit.equals(this)) {
                    unit.destroyBy(destroyer, false);
                }
            }
        }

        // destroy the unit itself
        civilization.removeUnit(this);
    }
}
