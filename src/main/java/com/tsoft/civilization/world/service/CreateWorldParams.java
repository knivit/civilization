package com.tsoft.civilization.world.service;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateWorldParams {

    private final String worldName;
    private final String mapSize;
    private final int mapWidth;
    private final int mapHeight;
    private final String mapConfiguration;
    private final String climate;
    private final String difficultyLevel;
}
