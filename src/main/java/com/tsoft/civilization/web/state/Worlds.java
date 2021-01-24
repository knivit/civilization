package com.tsoft.civilization.web.state;

import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worlds {
    private static final Map<String, World> worlds = new HashMap<>();

    public static synchronized boolean add(World world) {
        boolean nameAlreadyExists = worlds.values().stream()
            .anyMatch(w -> w.getName().equalsIgnoreCase(world.getName()));

        if (nameAlreadyExists) {
            return false;
        }

        worlds.put(world.getId(), world);
        return true;
    }

    public static synchronized World getWorld(String worldId) {
        return worlds.get(worldId);
    }

    public static synchronized List<World> getWorlds() {
        return new ArrayList<>(worlds.values());
    }
}
