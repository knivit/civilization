package com.tsoft.civilization.improvement;

import java.util.ArrayList;

public class ImprovementList extends ArrayList<AbstractImprovement> {

    public boolean has(String classUuid) {
        for (AbstractImprovement improvement : this) {
            if (classUuid.equals(improvement.getClassUuid())) {
                return true;
            }
        }
        return false;
    }
}
