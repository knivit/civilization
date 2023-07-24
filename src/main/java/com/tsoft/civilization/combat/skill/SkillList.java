package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.util.AList;

public class SkillList<T> extends AList<T> {

    public static final SkillList UNMODIFIABLE_EMPTY_LIST = (SkillList)new SkillList().makeUnmodifiable();

    public static <T> SkillList<T> of(T ... skills) {
        return new SkillList<T>().addAll(skills).makeUnmodifiable();
    }
}
