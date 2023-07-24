package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static org.assertj.core.api.Assertions.assertThat;

public class SkillServiceTest {

    private static final SkillService skillService = new SkillService();

    @Test
    void calc_pass_score() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers", new Point(2, 0))
        );

        assertThat(skillService.calcPassScore(world.get("settlers"), SkillList.of(BASE_MOVEMENT_SKILL)))
            .isEqualTo(2);
    }
}
