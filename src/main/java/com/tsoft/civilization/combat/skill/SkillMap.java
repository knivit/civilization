package com.tsoft.civilization.combat.skill;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class SkillMap<T extends AbstractSkill> implements Iterable<Map.Entry<T, SkillLevel>> {

    public static final SkillMap EMPTY = new SkillMap<>().unmodifiable();

    private final Map<T, SkillLevel> map = new TreeMap<>();
    private boolean isUnmodifiable;

    public SkillMap() { }

    public SkillMap(Map<T, SkillLevel> map) {
        this.map.putAll(map);
    }

    public SkillMap(Object ... kv) {
        put(kv);
    }

    public SkillMap<T> put(Object ... kv) {
        checkIsUnmodifiable();

        for (int i = 0; i < kv.length; i += 2) {
            map.put((T) kv[i], (SkillLevel) kv[i + 1]);
        }
        return this;
    }

    public SkillMap<T> unmodifiable() {
        SkillMap<T> map = new SkillMap<>(this.map);
        map.isUnmodifiable = true;
        return map;
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The map is unmodifiable");
        }
    }

    @Override
    public Iterator<Map.Entry<T, SkillLevel>> iterator() {
        return map.entrySet().iterator();
    }

    public Stream<Map.Entry<T, SkillLevel>> stream() {
        return map.entrySet().stream();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
