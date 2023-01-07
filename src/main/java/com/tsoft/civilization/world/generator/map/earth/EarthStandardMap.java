package com.tsoft.civilization.world.generator.map.earth;

import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.map.AbstractWorldMap;
import com.tsoft.civilization.world.generator.map.OriginalMapFileReader;

public class EarthStandardMap implements AbstractWorldMap {

    private static volatile Object LOCK = new Object();
    private static volatile String[] INSTANCE;

    @Override
    public String[] getMap() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    String fileName = this.getClass().getResource("/original/Earth_Standard.Civ5Map").getFile();
                    OriginalMapFileReader mapReader = new OriginalMapFileReader();
                    INSTANCE = mapReader.read(fileName, MapSize.STANDARD);
                }
            }
        }

        return INSTANCE;
    }
}
