package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatExperience;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.unit.service.UnitCombatService;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.service.UnitPromotionService;
import com.tsoft.civilization.unit.service.promotion.PromotionType;
import com.tsoft.civilization.unit.service.UnitMovementService;
import com.tsoft.civilization.unit.service.UnitProductionService;
import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.civilization.city.construction.CanBeBuilt;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasHistory;
import com.tsoft.civilization.world.World;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class AbstractUnit implements HasId, HasView, HasCombatStrength, HasHistory, CanBeBuilt {

    @Getter
    private String id;

    @Getter
    private final String classUuid;

    @Getter
    private final UnitBaseState baseState;

    @Getter
    private final AbstractUnitView view;

    @Getter @Setter
    private Civilization civilization;

    @Getter
    private UnitProductionService productionService;

    @Getter
    private UnitCombatService combatService;

    @Getter
    private UnitMovementService movementService;

    @Getter
    private UnitPromotionService promotionService;

    @Getter
    private boolean isDestroyed;

    // Initialization on create the object
    protected void init(Civilization civilization) {
        id = civilization.getWorld().getWorldObjectService().add(this);
        this.civilization = civilization;

        productionService = new UnitProductionService(this);
        combatService = new UnitCombatService(this);
        movementService = new UnitMovementService(this);
        promotionService = new UnitPromotionService(this);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return getBaseState().getCategory();
    }

    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }

    public int getPassCost(AbstractTerrain tile) {
        return getBaseState().getPassCostTable().getPassCost(civilization, tile);
    }

    public int getGoldCost(Civilization civilization) {
        double modifier = UnitBaseModifiers.getEconomicModifier(civilization);
        int goldCost = getBaseState().getGoldCost();
        return (int)Math.round(goldCost * modifier);
    }

    public int getGoldUnitKeepingExpenses() {
        double modifier = UnitBaseModifiers.getEconomicModifier(civilization);
        int expenses = getBaseState().getGoldUnitKeepingExpenses();
        return (int)Math.round(expenses * modifier);
    }

    @Override
    public int getBaseProductionCost(Civilization civilization) {
        double modifier = UnitBaseModifiers.getProductionCostModifier(civilization);
        int goldCost = getBaseState().getProductionCost();
        return (int)Math.round(goldCost * modifier);
    }

    @Override
    public CombatStrength getBaseCombatStrength(Civilization civilization) {
        UnitBaseState baseState = getBaseState();
        CombatStrength baseCombatStrength = baseState.getCombatStrength();

        double modifier = UnitBaseModifiers.getCombatStrengthModifier(civilization);

        return CombatStrength.builder()
            .rangedAttackRadius(baseCombatStrength.getRangedAttackRadius())
            .rangedAttackStrength(baseCombatStrength.getRangedAttackStrength() * modifier)
            .rangedBackFireStrength(baseCombatStrength.getRangedBackFireStrength() * modifier)
            .meleeAttackStrength(baseCombatStrength.getMeleeAttackStrength() * modifier)
            .meleeBackFireStrength(baseCombatStrength.getMeleeBackFireStrength() * modifier)
            .defenseStrength(baseCombatStrength.getDefenseStrength() * modifier)
            .build();
    }

    public SkillList<AbstractMovementSkill> getBaseMovementSkills(Civilization civilization) {
        UnitBaseState baseState = getBaseState();
        return new SkillList<AbstractMovementSkill>().addAll(baseState.getMovementSkills());
    }

    @Override
    public SkillList<AbstractCombatSkill> getBaseCombatSkills(Civilization civilization) {
        UnitBaseState baseState = getBaseState();
        return new SkillList<AbstractCombatSkill>().addAll(baseState.getCombatSkills());
    }

    @Override
    public SkillList<AbstractHealingSkill> getBaseHealingSkills(Civilization civilization) {
        UnitBaseState baseState = getBaseState();
        return new SkillList<AbstractHealingSkill>().addAll(baseState.getHealingSkills());
    }

    public boolean hasPromotions(PromotionType ... promotions) {
        return promotionService.hasPromotions(promotions);
    }

    @Override
    public void startYear() {
        movementService.startYear();
        combatService.startYear();
    }

    public void startEra() {
        combatService.startEra();
    }

    @Override
    public void stopYear() {
        combatService.stopYear();
    }

    public World getWorld() {
        return civilization.getWorld();
    }

    public TilesMap getTilesMap() {
        return getWorld().getTilesMap();
    }

    @Override
    public Point getLocation() {
        return movementService.getLocation();
    }

    public AbstractTerrain getTile() {
        return getTilesMap().getTile(getLocation());
    }

    @Override
    public CombatStrength calcCombatStrength() {
        return combatService.calcCombatStrength();
    }

    @Override
    public CombatDamage getCombatDamage() {
        return combatService.getCombatDamage();
    }

    @Override
    public void addCombatDamage(CombatDamage damage) {
        combatService.addCombatDamage(damage);
    }

    @Override
    public void setCombatExperience(CombatExperience experience) {
        combatService.setCombatExperience(experience);
    }

    public int getPassScore() {
        return movementService.getPassScore();
    }

    @Override
    public void setPassScore(int passScore) {
        movementService.updatePassScore(passScore);
    }

    public boolean canBeCaptured() {
        return !getUnitCategory().isMilitary();
    }

    @Override
    public Supply calcPillageSupply() {
        return Supply.builder().gold(30).build();
    }

    @Override
    public boolean canBeDestroyedByRangedAttack() {
        return false;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    public boolean isActionAvailable() {
        return !isDestroyed() && (movementService.getPassScore() > 0);
    }

    public boolean isOnMyTerritory() {
        return civilization.getTileService().isOnTerritory(movementService.getLocation());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "civilization=" + civilization +
            ", location=" + getLocation() +
            ", passScore=" + getPassScore() +
            ", isDestroyed=" + isDestroyed +
        "}";
    }
}
