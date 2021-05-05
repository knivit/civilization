package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceType;
import lombok.Getter;

import java.util.UUID;

public class Wheat extends AbstractResource {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final ResourceType resourceType = ResourceType.EARTH;
}
