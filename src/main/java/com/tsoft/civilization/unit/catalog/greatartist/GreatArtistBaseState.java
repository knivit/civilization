package com.tsoft.civilization.unit.catalog.greatartist;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;

import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static java.util.Arrays.asList;

public class GreatArtistBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build();
    }
}
