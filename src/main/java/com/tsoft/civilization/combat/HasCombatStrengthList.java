package com.tsoft.civilization.combat;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.List;

public class HasCombatStrengthList extends ArrayList<HasCombatStrength> {
    public List<Point> getLocations() {
        List<Point> locations = new ArrayList<Point>(size());
        for (HasCombatStrength combatant : this) {
            locations.add(combatant.getLocation());
        }
        return locations;
    }
}
