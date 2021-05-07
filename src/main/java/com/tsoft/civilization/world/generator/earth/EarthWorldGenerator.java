package com.tsoft.civilization.world.generator.earth;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileFactory;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.WorldGeneratorHelper;

public class EarthWorldGenerator implements WorldGenerator {

    private static final WorldGeneratorHelper helper = new WorldGeneratorHelper();

    @Override
    public void generate(TilesMap tilesMap, Climate climate) {
        helper.fill(tilesMap, Ocean.CLASS_UUID);

        defineContinents(tilesMap);
    }

    // 40x24 (according to minimal DUEL map size)
    //
    //                       10  12  14  16  18  20  22 24 26 28 30 32 34 36 38
    //    0 1 2 3 4 5 6 7 8 9  11  13  15  17  19  21  23 25 27 29 31 33 35 37 39
    //    -----------------------------------------------------------------------
    private static final String[] map = new String[] {
        ". . . . . . . . . . . . x x x . . . . . . . . . . . . . . . . . . . . . ", //  0
        ". . . . . . . x x . . x x x x . . . . . . . . . . . . . . . . . . . . . ", //  1
        ". . . . . x x x . . . x x x x x . . . . . x . . x . . x x . . x . . . . ", //  2
        ". . . . x x . x x . . . x x x . . . x . x x x x x . . x x x x x x x x . ", //  3
        "x x x x . x x x x . x . . x . . . x . x x x x x x x x x x x x x x x x . ", //  4
        ". x x x x x x x x x x . . . . . . . x x x x x x x x x x x x x x x x . . ", //  5
        "x x x x x x x x x . . x . . . . . . x x x x x x x x x x x x x x x . . . ", //  6
        ". x x x x x x x x . . . . . . . . x x x x x x x x x x x x x x x . . . . ", //  7
        ". . x x x x x x . . x x . . . . . x x x x x . x . x x x x x x x x . . . ", //  8
        ". . x x x x x x x x x x . . . . x x x x x x . x x x x x x x x x x . . . ", //  9
        ". . . x x x x x x x x . . . . . . . . . . . . . x x x x x x x x x x . . ", // 10
        ". . . x x x x x x x . . . . . x x x x x x x x . . x x x x x x x x . . . ", // 11
        ". . . . x x x . x . . . . . x x x x x x x x x x x . x x x x x x . . . . ", // 12
        ". . . . . x . . . . . . . . x x x x x x x x x x . . . . x x . . . . . . ", // 13
        ". . . . . x . . . . . . . . . x x x x x x x x . . . . . . . x x . . . . ", // 14
        ". . . . x x x x . . . . . . . . . x x x x x .x. . . . . . . . . x x . . ", // 15
        ". . . . x x x x x x . . . . . . . x x x x x x . . . . . . . . . . . . . ", // 16
        ". . . . x x x x x x x x . . . . x x x x x x . . . . . . . . . . . . . . ", // 17
        ". . . . . x x x x x x x x . . . x x x x x x . . . . . . . . . x x x . . ", // 18
        ". . . . . . . x x x x x . . . . . x x x x . x . . . . . . . x x x x . . ", // 19
        ". . . . . . . x x x x . . . . . . x x x . x x . . . . . . . x . . x . . ", // 20
        ". . . . . . . x x x . . . . . . . . x . . . . . . . . . . . . . . . . . ", // 21
        ". . . . . . . x x . . . . . . . . . . . . . . . . . . . . . . . x x . . ", // 22
        ". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . "  // 23
    };

    private void defineContinents(TilesMap tilesMap) {
        // horizontal
        for (int y = 0; y < 24; y ++) {
            int s = -1;
            for (int x = 0; x < 40; x ++) {
                if (map[y].charAt(x) == 'x') {
                    if (s == -1) {
                        s = x;
                    }
                } else {
                    if (s != -1) {
                        fillHorizontal(tilesMap, y, s, x);
                        s = -1;
                    }
                }
            }
        }

        // vertical
        for (int x = 0; x < 40; x ++) {
            int s = -1;
            for (int y = 0; y < 24; y ++) {
                if (map[y].charAt(x) == 'x') {
                    if (s == -1) {
                        s = x;
                    }
                } else {
                    if (s != -1) {
                        fillVertical(tilesMap, x, s, y);
                        s = -1;
                    }
                }
            }
        }
    }

    private void fillHorizontal(TilesMap tilesMap, int y, int fromX, int toX) {
        double cx = tilesMap.getWidth() / 40.0;
        double cy = tilesMap.getHeight() / 24.0;

        int fx = (int)Math.round(cx * fromX);
        int tx = (int)Math.round(cx * toX);
        int ay = (int)Math.round(cy * y);

        for (int ax = fx; ax <= tx; ax ++) {
            AbstractTile tile = TileFactory.newInstance(Grassland.CLASS_UUID);
            tilesMap.setTile(new Point(ax, ay), tile);
        }
    }

    private void fillVertical(TilesMap tilesMap, int x, int fromY, int toY) {
        double cx = tilesMap.getWidth() / 40.0;
        double cy = tilesMap.getHeight() / 24.0;

        int fy = (int)Math.round(cy * fromY);
        int ty = (int)Math.round(cy * toY);
        int ax = (int)Math.round(cx * x);

        for (int ay = fy; ay <= ty; ay ++) {
            AbstractTile tile = TileFactory.newInstance(Grassland.CLASS_UUID);
            tilesMap.setTile(new Point(ax, ay), tile);
        }
    }
}
