package com.tsoft.civilization.world.generator.earth;

import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.web.render.MapRender;
import org.junit.jupiter.api.Test;

public class EarthWorldGeneratorTest {

    @Test
    public void generate() {
        new MapRender(EarthWorldGeneratorTest.class)
            .createPng(MockTilesMap.of(EarthDuelMap.MAP));
    }
}
