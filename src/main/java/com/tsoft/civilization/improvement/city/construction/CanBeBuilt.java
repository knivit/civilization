package com.tsoft.civilization.improvement.city.construction;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;

public interface CanBeBuilt extends HasId, HasView {
    String getClassUuid();

    int getProductionCost(Civilization civilization);
}
