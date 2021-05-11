package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.L10n.L10n;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static com.tsoft.civilization.combat.skill.L10nSkill.CAN_CONQUER_CITY;

@Getter
@Builder
@ToString
public class CanConquerCitySkill implements AbstractSkill {

    @Getter
    private final L10n localizedName = CAN_CONQUER_CITY;
}
