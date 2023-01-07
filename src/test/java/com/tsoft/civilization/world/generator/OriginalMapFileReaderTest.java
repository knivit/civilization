package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.web.render.MapRender;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.map.OriginalMapFileReader;
import org.junit.jupiter.api.Test;

public class OriginalMapFileReaderTest {

    private static final OriginalMapFileReader MAP_FILE_READER = new OriginalMapFileReader();

    @Test
    public void main() {
        String fileName = OriginalMapFileReader.class.getResource("/original/Earth_Standard.Civ5Map").getFile();
        String[] lines = MAP_FILE_READER.read(fileName, MapSize.STANDARD);

        new MapRender(this.getClass()).createPng(MockTilesMap.of(2, lines));
    }
}
