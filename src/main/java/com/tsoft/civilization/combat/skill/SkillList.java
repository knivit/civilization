package com.tsoft.civilization.combat.skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SkillList implements Iterable<AbstractSkill> {

    private final List<AbstractSkill> skills = new ArrayList<>();
    private boolean isUnmodifiable;

    public SkillList() { }

    public SkillList(List<AbstractSkill> skills) {
        Objects.requireNonNull(skills);
        this.skills.addAll(skills);
    }

    public List<AbstractSkill> getListCopy() {
        return new ArrayList<>(skills);
    }

    public SkillList unmodifiableList() {
        SkillList list = new SkillList();
        list.skills.addAll(skills);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<AbstractSkill> iterator() {
        return skills.iterator();
    }

    public Stream<AbstractSkill> stream() {
        return skills.stream();
    }

    public AbstractSkill getAny() {
        return skills.isEmpty() ? null : skills.get(0);
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public SkillList add(AbstractSkill skill) {
        checkIsUnmodifiable();
        skills.add(skill);
        return this;
    }

    public SkillList addAll(SkillList skillList) {
        checkIsUnmodifiable();

        if (skillList != null && !skillList.isEmpty()) {
            for (AbstractSkill unit : skillList ) {
                skills.add(unit);
            }
        }
        return this;
    }

    public SkillList remove(AbstractSkill skill) {
        skills.remove(skill);
        return this;
    }

    public boolean isEmpty() {
        return skills.isEmpty();
    }

    public int size() {
        return skills.size();
    }

}
