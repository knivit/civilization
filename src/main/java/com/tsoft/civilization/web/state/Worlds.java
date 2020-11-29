package com.tsoft.civilization.web.state;

import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worlds {
    private static final Map<String, World> worlds = new HashMap<>();

    public static synchronized void add(World world) {
        worlds.put(world.getId(), world);
    }

    public static World getWorld(String worldId) {
        return worlds.get(worldId);
    }

    public static synchronized List<World> getWorlds() {
        return new ArrayList<>(worlds.values());
    }
}
