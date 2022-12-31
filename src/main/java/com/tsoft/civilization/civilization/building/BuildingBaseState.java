package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.economic.Supply;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuildingBaseState {

    private final Supply supply;
}
