package com.tsoft.civilization.web.state;

import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Worlds {
    private static final int MAX_WORLDS_COUNT = 16;

    private static final Map<String, World> worlds = new HashMap<>();

    public static synchronized boolean add(World world) {
        if (worlds.size() >= MAX_WORLDS_COUNT) {
            log.info("All available worlds are created, max size = {}", MAX_WORLDS_COUNT);
            return false;
        }

        boolean nameAlreadyExists = worlds.values().stream()
            .anyMatch(w -> w.getName().equalsIgnoreCase(world.getName()));

        if (nameAlreadyExists) {
            log.info("World named {} already exists", world.getName());
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

    public static synchronized void remove(String worldId) {
        worlds.remove(worldId);
    }

    public static synchronized void clearAll() {
        worlds.clear();
    }
}
