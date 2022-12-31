package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.economic.Supply;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResourceBaseState {

    private final Supply supply;
}
