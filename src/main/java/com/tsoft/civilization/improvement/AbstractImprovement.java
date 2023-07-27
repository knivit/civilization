package com.tsoft.civilization.improvement;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatExperience;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.SkillList;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
public abstract class AbstractImprovement implements HasSupply, HasCombatStrength {

    private static final CombatStrength BASE_COMBAT_STRENGTH = CombatStrength.builder()
        .defenseStrength(0)
        .build();

    @Getter
    private String id;

    @Getter
    private AbstractTerrain tile;

    @Getter
    private City city;

    private boolean isPillaged;

    public abstract String getClassUuid();
    public abstract ImprovementBaseState getBaseState();
    public abstract AbstractImprovementView getView();
    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTerrain tile);

    protected void init(AbstractTerrain tile, World world, City city) {
        id = world.getWorldObjectService().add(this);

        this.city = city;
        this.tile = tile;

        tile.addImprovement(this);
    }

    @Override
    public Supply getBaseSupply(Civilization civilization) {
        Supply baseSupply = getBaseState().getSupply();
        double modifier = ImprovementBaseModifiers.getModifier(civilization);
        return baseSupply.applyModifier(modifier);
    }

    @Override
    public CombatStrength getBaseCombatStrength(Civilization civilization) {
        return BASE_COMBAT_STRENGTH;
    }

    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        if (isPillaged) {
            return Supply.EMPTY;
        }

        return getBaseSupply(civilization);
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        return Supply.EMPTY;
    }


    @Override
    public Civilization getCivilization() {
        return (city == null) ? null : city.getCivilization();
    }

    @Override
    public Point getLocation() {
        return tile.getLocation();
    }

    @Override
    public UnitCategory getUnitCategory() {
        return null;
    }

    @Override
    public SkillList<AbstractCombatSkill> getBaseCombatSkills(Civilization civilization) {
        return SkillList.UNMODIFIABLE_EMPTY_LIST;
    }

    @Override
    public CombatStrength calcCombatStrength() {
        return BASE_COMBAT_STRENGTH;
    }

    @Override
    public SkillList<AbstractHealingSkill> getBaseHealingSkills(Civilization civilization) {
        return SkillList.UNMODIFIABLE_EMPTY_LIST;
    }

    @Override
    public CombatDamage getCombatDamage() {
        return null;
    }

    @Override
    public void addCombatDamage(CombatDamage damage) {

    }

    @Override
    public void setCombatExperience(CombatExperience experience) {

    }

    @Override
    public void setPassScore(int passScore) {

    }

    @Override
    public Supply calcPillageSupply() {
        return Supply.builder().gold(30).build();
    }

    public void repair() {
        isPillaged = false;
    }

    @Override
    public boolean isDestroyed() {
        return isPillaged;
    }

    @Override
    public void destroy() {
        isPillaged = true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
            "name=" + ((getView() == null) ? "null" : getView().getLocalizedName()) +
            ", location=" + tile.getLocation() +
        '}';
    }
}
