package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.util.l10n.L10n;

public interface AbstractSkill extends Comparable<AbstractSkill> {

    L10n getLocalizedName();

    @Override
    default int compareTo(AbstractSkill other) {
        return getClass().getName().compareTo(other.getClass().getName());
    }
}
