package com.tsoft.civilization.world.service;

import com.tsoft.civilization.world.HasId;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldObjectService {

    private final Map<UUID, HasId> objects = new HashMap<>();

    public String add(HasId obj) {
        UUID id = UUID.randomUUID();
        objects.put(id, obj);
        return id.toString();
    }

    public <E extends HasId> E get(String id) {
        UUID uuid = UUID.fromString(id);
        return (E)objects.get(uuid);
    }
}
