package com.tsoft.civilization.civilization.city.construction;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;

public interface CanBeBuilt extends HasId, HasView {
    String getClassUuid();

    int getBaseProductionCost(Civilization civilization);
}
