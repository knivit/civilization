package com.tsoft.civilization.improvement;

import com.tsoft.civilization.common.HasId;
import com.tsoft.civilization.common.HasView;

public interface CanBeBuilt extends HasId, HasView {
    String getClassUuid();

    int getProductionCost();
}
