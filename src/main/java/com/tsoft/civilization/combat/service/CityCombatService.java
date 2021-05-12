package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.HasHistory;
import lombok.Getter;

/**
 * Introduction
 * ------------
 * Cities are the most important targets in the world, but they are big, and if fortified and defended by other units
 * can be quite difficult to capture. However, doing so can reap rich rewards - in fact, the only way to knock another
 * civilization out of the game is to capture or destroy all of its cities. Also, this is one of the paths to victory
 * in the game - the Domination victory.
 *
 * City combat statistics
 * ----------------------
 * Every city has defenses, like arrow slits or gun holes, and anti-air guns in Modern ages. It also has an automatic
 * defense unit, which mans the defenses and repels enemy attacks, and performs the city's Ranged attack each turn.
 * This unit uses weapons determined by your current military technology, meaning that it becomes more effective as Eras pass.
 * The unit is protected by the city's defenses, and always fights at its full strength, not to mention whether there are
 * additional defenders in the city, or any defensive structures - all these can increase the fighting strength,
 * but a city will always have a minimum fight capabilities, ensuring that an enemy can't simply walk in and
 * take possession of it.
 *
 * Health
 * ------
 * This stat represents the current state of the city defenses. An undamaged city has 200 hit points. More are added
 * to this maximum by constructing the city defense structures: Walls, Castle, Arsenal and Military base.
 * As the city takes damage in combat, its hit points are reduced.
 *
 * If a city's hit points reach 1, any enemy unit with melee attack (and only melee attack!!!) can capture the city
 * by entering its tile.
 *
 * Note also that cities Heal automatically each turn (being constantly repaired by their inhabitants),
 * making them even harder to capture. The amount healed is about 10 - 15% of its total health (subject to verification).
 *
 * Combat strength
 * ---------------
 * A city also has Combat Strength (CS), representing its military might, compared to attacking units.
 * This strength isn't constant, like for units, because a city endures through the ages, while units become gradually obsolete.
 * The following factors are used to determine the Combat Strength of a city:
 *     Base - A basic factor, determined by the current era of the civilization. For example, while in the Ancient Era,
*             a city has a base CS of about 9 - 10, but in the Modern Era, the base increases to 50 - 60.
 *     Vantage - If the city is constructed on a Hill terrain, it has an advantage over attackers, which translates into
 *            additional CS.
 *     Population - Each citizen increases CS.
 *     Defenses - If you build defensive structures in the city (Walls, Castle, Arsenal and Military Base), each of those
 *            will increase CS.
 *     Garrison - If you station a land unit (not naval or air unit) in the same tile as the city, it is considered 'Garrisoned'.
 *            While it is garrisoned, the city's CS increases by several points.
 *
 * The city CS determines the damage it does with its Ranged attack, and whenever it's attacked by melee or air units
 * (which triggers a retaliation attack, just like normal Melee combat). Lower health doesn't affect a city's strength
 * as it does for units - a city always fights at its full strength!
 *
 * Ranged attack
 * -------------
 * Every city has the ability to shoot at attacking armies. The attack has a range of 2, complete visibility
 * (no terrain obstacles impede it), and is always at 40% of the city's full combat strength (60% with Oligarchy),
 * even if the city itself is damaged. Bonus city strength gained from defensive structures is not applied when the city attacks.
 * Being a ranged attack, the targets cannot retaliate.
 *
 * City combat
 * -----------
 * All unit types can attack a city and damage it, if in range. Melee attacks trigger a retaliation attack, as normal.
 * Melee naval units can attack a city from an ocean tile bordering it. Ranged attacks are safe - that is,
 * the city can't retaliate to them; note however, that a ranged attack cannot drop a city below 1 HP;
 * the city must be captured by a melee unit.
 *
 * All in all, an army attacking a city is always at a disadvantage. The city has considerable combat strength
 * (which is usually higher than the average strength of the attackers, even without additional bonuses) and always
 * fights at its full strength, while the attacking units' strength declines when they're damaged;
 * The city has a powerful ranged attack that can devastate and even kill units in one shot. Finally, it can garrison
 * units inside, keeping them safe while they attack the besieging army.
 *
 * Attacking cities with melee units
 * ---------------------------------
 * When a unit engages in melee combat with a city, the city and the melee unit both suffer damage.
 * No matter how few hit points the city has remaining, it always defends itself at its full combat strength.
 * Usually, a city's combat strength for the relevant age is larger than that of the attacking unit
 * (especially if the city has defenses), so attackers are always at a disadvantage.
 * However, only melee units can capture cities, so they have to attack eventually.
 *
 * Garrisoned units in cities
 * --------------------------
 * A city's owner may station a military unit inside the city to bolster its defenses. Note that while all types of
 * units positioned in the city's tile are completely invulnerable to attacks, only Land units may form a Garrison!
 * So, for example, a city may have a ship and airplanes in its tile, but none of them would add to the city's CS;
 * while a single Swordsman will serve as Garrison, even though it's not a Ranged unit.
 *
 * A portion of the garrisoned unit's combat strength is added to the city's total strength.
 * The garrisoned unit will take no damage when the city is attacked; and it is completely safe when using a Ranged attack.
 * If the unit is melee, however, it will still suffer damage when attacking surrounding units (in real life this is
 * called a Sortie, and as we know, a melee attack takes place in the target's tile, not the attacker, so technically
 * the garrison unit DOES go out of the city to make the attack!). Note also that a unit stationed in the city may
 * attack surrounding enemy units while the city will still receive its garrison bonus, as long as the unit doesn't end
 * its turn outside the city.
 *
 * If the city is captured, the garrisoned unit (as well as all other military units) is destroyed; any applicable
 * Civilian units are either captured, or destroyed.
 *
 * Siege Weapons
 * -------------
 * Certain ranged weapons are classified as "siege weapons" - catapults, trebuchets, and so forth.
 * These units get large combat bonuses (+200%) when attacking enemy cities. They are extremely vulnerable to melee combat,
 * and should be accompanied by melee units to fend off enemy assault.
 *
 * Most siege weapons have to be disassembled to move around the map. When they have reached their destination,
 * they must expend a movement point to "set up". They cannot attack until they have done so.
 *
 * Siege weapons are important. It's really difficult to capture a well-defended city without them!
 *
 * Capturing cities
 * ----------------
 * When a city's hit points reach "1", an enemy melee unit may enter the city, regardless of whether or not there are
 * any units already inside. When this occurs, the city is captured. Any units which were stationed inside, be it land,
 * naval or air units (including missiles), and Great People, are destroyed. Civilian units, such as Workers,
 * are captured according to the normal rules. All defensive buildings, such as Walls and Castles, are destroyed and
 * need to be rebuilt later by the new owner. A random selection of other Buildings is also destroyed.
 * Finally, the Population of the captured city is diminished in half, presumably killed during the looting of the city.
 * The city will slowly regain its health, starting at somewhere around 40% Health at the turn of capture.
 *
 * The newly conquered city will also enter Resistance mode - a state when the surviving Population is silently opposing
 * the occupation, and simply refuses to work for the new owner. This mode continues for as many turns as there is Population
 * after the city capture. Resistance doesn't occur if you've conquered back a city you've originally founded
 * (meaning that you've liberated your own city which was under enemy occupation), or when you liberate the city and give
 * it back to its original owner, whomever this was.
 *
 *     Note: As of the Fall 2013 patch, Population loss and Resistance may be reduced if you have some degree of cultural
 *     influence over the civilization which owned the city. There is a 25% reduction in both factors for each level of
 *     Tourism influence beyond Exotic: -25% for Familiar, -50% for Popular, -75% for Influential and no Resistance
 *     and Population loss for Dominant.
 *
 * The conqueror loots an amount of Gold from the city, according to its size; he also receives all Great Works that were
 * stationed in the city. Finally, he receives a choice of what to do with it (for more info on the available choices,
 * visit this article), and if he chooses to Annex or Puppet it, territorial borders that belonged to that city pass
 * to his nation. Note that the game remembers each and every tile a city has conquered in its lifetime -
 * it's exactly those tiles that change owners when a city is captured. All Resources the city controls also pass to the
 * conqueror (unless their improvements were pillaged). Whatever the choice, the civilization which loses the city has taken
 * a huge blow.
 *
 * Conquering City-States
 * ----------------------
 * When a civilization captures a City-State, the same loses its status as City-State and turns into a normal city for
 * that civilization. Its special features (that is, bonuses for being Religious, Maritime etc.) simply disappear,
 * and the city is counted as just another city, controlling some territory, etc. The calculation for achieving Diplomatic
 * victory also changes to reflect the diminishing of the overall number of City-States in the game.
 *
 * City-States cannot be Razed, however. That means that another civilization may later Liberate the City-State,
 * restoring its special status and reversing all of the above changes.
 */
public class CityCombatService implements HasHistory {

    private final SkillService skillService = new SkillService();

    private final City city;

    @Getter
    private final UnitCategory unitCategory = UnitCategory.MILITARY_RANGED_CITY;

    @Getter
    private CombatDamage combatDamage = CombatDamage.builder()
        .damage(0)
        .build();

    private final SkillMap<AbstractCombatSkill> combatSkills;
    private final SkillMap<AbstractHealingSkill> healingSkills;

    public CityCombatService(City city) {
        this.city = city;

        int era = city.getCivilization().getWorld().getEra();
        combatSkills = city.getBaseCombatSkills(era);
        healingSkills = city.getBaseHealingSkills(era);
    }

    public CombatStrength getBaseCombatStrength(int era) {
        return CombatStrength.builder()
            .rangedAttackStrength(10)
            .rangedAttackRadius(2)
            .rangedBackFireStrength(4)
            .defenseStrength(50)
            .build();
    }

    public CombatStrength calcCombatStrength() {
        CombatStrength strength = skillService.calcCombatStrength(city, combatSkills);
        return strength.minus(combatDamage);
    }

    public void addCombatDamage(CombatDamage damage) {
        combatDamage = combatDamage.add(damage);
    }

    public SkillMap<AbstractCombatSkill> getCombatSkills() {
        return combatSkills.unmodifiable();
    }

    public SkillMap<AbstractHealingSkill> getHealingSkills() {
        return healingSkills.unmodifiable();
    }

    public void addCombatSkill(AbstractCombatSkill skill) {
        combatSkills.put(skill, SkillLevel.ONE);
    }

    public void addHealingSkill(AbstractHealingSkill skill) {
        healingSkills.put(skill, SkillLevel.ONE);
    }

    @Override
    public void startYear() {

    }

    public void startEra() {

    }

    @Override
    public void stopYear() {
        combatDamage = skillService.applyHealingSkills(city, healingSkills);
    }
}
