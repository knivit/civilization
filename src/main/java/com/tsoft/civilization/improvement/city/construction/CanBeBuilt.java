package com.tsoft.civilization.improvement.city.construction;

import com.tsoft.civilization.common.HasId;
import com.tsoft.civilization.common.HasView;

public interface CanBeBuilt extends HasId, HasView {
    String getClassUuid();

    int getProductionCost();
}
