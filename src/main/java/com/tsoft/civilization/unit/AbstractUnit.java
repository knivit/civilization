package com.tsoft.civilization.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.util.UnitCatalog;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitType;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.unit.AbstractUnitView;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.World;

import java.lang.reflect.Constructor;
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
    public abstract void initPassScore();
    public abstract int getGoldCost();
    public abstract V getView();
    public abstract String getClassUuid();

    // Method of a unit from the catalog (they don't have civilization etc)
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    protected abstract CombatStrength getBaseCombatStrength();

    public static <T extends AbstractUnit> T newInstance(T unit, Civilization civilization, Point location) {
        TilesMap tilesMap = civilization.getTilesMap();
        if ((location.getX() < 0 || location.getX() >= tilesMap.getWidth()) ||
                (location.getY() < 0 || location.getY() >= tilesMap.getHeight())) {
            DefaultLogger.severe("Invalid location " + location.toString() + ", must be [0.." + (tilesMap.getWidth() - 1) + ",0.." + (tilesMap.getHeight() - 1) + "]", new IllegalArgumentException());
            return null;
        }

        try {
            Constructor constructor = unit.getClass().getConstructor();
            unit = (T)constructor.newInstance();
            unit.init(civilization, location);

            return unit;
        } catch (Exception ex) {
            DefaultLogger.severe("Can't create an object", ex);
        }
        return null;
    }

    protected AbstractUnit() { }

    public static UnitCollection getUnitCatalog() {
        return UnitCatalog.values();
    }

    public static AbstractUnit getUnitFromCatalogByClassUuid(String classUuid) {
        return UnitCatalog.values().findByClassUuid(classUuid);
    }

    // Initialization on create the object
    protected void init(Civilization civilization, Point location) {
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
        return !getUnitType().isMilitary();
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
