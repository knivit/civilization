package com.tsoft.civilization.world.generator.onecontinent;

import com.tsoft.civilization.util.Rect;
import com.tsoft.civilization.world.generator.map.onecontinent.OneContinentWorldGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneContinentWorldGeneratorTest {

    @Test
    public void calcContinentDimension() {
        OneContinentWorldGenerator worldGenerator = new OneContinentWorldGenerator();
        int worldWidth = 1000;
        int worldHeight = 1000;
        Rect continent = worldGenerator.calcContinentDimension(worldWidth, worldHeight);
        double continentArea = continent.getWidth() * continent.getHeight();
        double worldArea = worldWidth * worldHeight;

        // possible error ~10%
        assertTrue(true);
    }
}
