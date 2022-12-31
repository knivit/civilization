package com.tsoft.civilization.world.generator.map.earth;

import com.tsoft.civilization.world.generator.map.AbstractWorldMap;
import lombok.Getter;

public class EarthDuelMap implements AbstractWorldMap {

    // 40x24
    @Getter
    private final String[] map = new String[] {
        "   0 1 2 3 4 5 6 7 8 9|0 1 2 3 4 5 6 7 8 9|0 1 2 3 4 5 6 7 8 9|0 1 2 3 4 5 6 7 8 9",
        "----------------------------------------------------------------------------------",
        "0|. . . . . . . . . . . . g g g . . . . . . . . . . . . . . . . . . . . . . . . . ",
        "1| . . . . . . . g g . . g g g g . . . . . . . . . . . . . . . . . . . . . . . . .",
        "2|. . . . . g g g . . . g g g g g . . . . . g . . g . . g g . . g . . . . . . . . ",
        "3| . . . . g g . g g . . . g g g . . . g . g g g g g . . g g g g g g g g g g g . .",
        "4|g g g g . g g g g . g . . g . . . g . g g g g g g g g g g g g g g g g g g . . . ",
        "5| . g g g g g g g g g g . . . . . . . g g g g g g g g g g g g g g g g g . g . . .",
        "6|g g g g g g g g g . . g . . . . . g g g g g g g g g g g g g g g g g g . g . . . ",
        "7| . g g g g g g g g . . . . . . . g g g g g g g g g g g g g g g g g g . g . . . .",
        "8|. . g g g g g g . . g . . . . . g g g g g g . g . g g g g g g g g . . . . . . . ",
        "9| . g g g g g g g g g . . . . . . . . . . . . . g g g g g g g g g g . . . . . . .",
        "0|. . g g g g g g g g . . . . . . . g g g g g . . g g g g g g g g g g . . . . . . ",
        "1| . . g g g g g g g . . . . . . g g g g g g g g . g g g g g g g g g . . . . . . .",
        "2|. . . . g g g . g . . . . . . g g g g g g g g g . . g g g g g g . . . . . . . . ",
        "3| . . . . g . . . . . . . . . g g g g g g g g g g . . . g g g . . . . . . g . . .",
        "4|. . . . . g . . . . . . . . . g g g g g g g g . . . . . . g g . . . . . . . . . ",
        "5| . . . . g g g g . . . . . . . . . g g g g g .g. . . . . . . . . g g . . . . . .",
        "6|. . . . g g g g g g . . . . . . . g g g g g g . . . . . . . . . . . . . . . . . ",
        "7| . . . . g g g g g g . . . . . . g g g g g g . . . . . . . . . . . . . . . . . .",
        "8|. . . . . g g g g g g . . . . . g g g g g g . . . . . . . . . . . g g g . . . . ",
        "9| . . . . . g g g g g . . . . . . . g g g g . g . . . . . . . . . g g g g . . . .",
        "0|. . . . . g g g g g . . . . . . . g g g . . g g . . . . . . . . . g g g . . . . ",
        "1| . . . . . g g g g . . . . . . . . . g . . g g . . . . . . . . . . . . . . . . .",
        "2|. . . . . . . g g . . . . . . . . . . . . . . . . . . . . . . . . . g g . . . . ",
        "3| . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . ."
    };
}
