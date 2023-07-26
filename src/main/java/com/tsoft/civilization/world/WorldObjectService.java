package com.tsoft.civilization.world;

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

    public HasId get(String id) {
        UUID uuid = UUID.fromString(id);
        return objects.get(uuid);
    }
}
