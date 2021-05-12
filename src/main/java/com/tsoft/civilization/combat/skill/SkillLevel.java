package com.tsoft.civilization.combat.skill;

import lombok.Getter;

public enum SkillLevel {

    ONE(1),
    TWO(2),
    THREE(3);

    @Getter
    private final int value;

    SkillLevel(int value) {
        this.value = value;
    }
}
