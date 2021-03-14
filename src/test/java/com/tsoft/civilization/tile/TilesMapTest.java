package com.tsoft.civilization.tile;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TilesMapTest {

    @Test
    public void getTilesAround1() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 4 5 6 7 ",
            "-+----------------",
            "0|. . . . . . . . ",
            "1| . . . . . . . .",
            "2|. . . . . . . . ",
            "3| . . . . . . . .",
            "4|. . . . . . . . ",
            "5| . . . . . . . .",
            "6|. . . . . . . . ",
            "7| . . . . . . . .");

        // round 1
        List<Point> locations = map.getLocationsAround(new Point(3, 3), 1);

        assertEquals(6, locations.size());
        assertEquals(new Point(3, 2), locations.get(0));
        assertEquals(new Point(4, 2), locations.get(1));
        assertEquals(new Point(4, 3), locations.get(2));
        assertEquals(new Point(4, 4), locations.get(3));
        assertEquals(new Point(3, 4), locations.get(4));
        assertEquals(new Point(2, 3), locations.get(5));
    }

    @Test
    public void getTilesAround2() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 4 5 6 7 ",
            "-+----------------",
            "0|. . . . . . . . ",
            "1| . . . . . . . .",
            "2|. . . . . . . . ",
            "3| . . . . . . . .",
            "4|. . . . . . . . ",
            "5| . . . . . . . .",
            "6|. . . . . . . . ",
            "7| . . . . . . . .");

        // round 2
        List<Point> locations = map.getLocationsAround(new Point(3, 3), 2);

        assertEquals(6 + 12, locations.size());

        // from round 1
        assertEquals(new Point(3, 2), locations.get(0));
        assertEquals(new Point(4, 2), locations.get(1));
        assertEquals(new Point(4, 3), locations.get(2));
        assertEquals(new Point(4, 4), locations.get(3));
        assertEquals(new Point(3, 4), locations.get(4));
        assertEquals(new Point(2, 3), locations.get(5));

        // from round 2
        assertEquals(new Point(2, 1), locations.get(6));
        assertEquals(new Point(3, 1), locations.get(7));
        assertEquals(new Point(4, 1), locations.get(8));
        assertEquals(new Point(5, 2), locations.get(9));
        assertEquals(new Point(5, 3), locations.get(10));
        assertEquals(new Point(5, 4), locations.get(11));
        assertEquals(new Point(4, 5), locations.get(12));
        assertEquals(new Point(3, 5), locations.get(13));
        assertEquals(new Point(2, 5), locations.get(14));
        assertEquals(new Point(2, 4), locations.get(15));
        assertEquals(new Point(1, 3), locations.get(16));
        assertEquals(new Point(2, 2), locations.get(17));
    }

    @Test
    public void getTilesAround3() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 4 5 6 7 ",
            "-+----------------",
            "0|. . . . . . . . ",
            "1| . . . . . . . .",
            "2|. . . . . . . . ",
            "3| . . . . . . . .",
            "4|. . . . . . . . ",
            "5| . . . . . . . .",
            "6|. . . . . . . . ",
            "7| . . . . . . . .");

        // round 3
        List<Point> locations = map.getLocationsAround(new Point(3, 3), 3);

        assertEquals(6 + 12 + 18, locations.size());

        // from round 1
        assertEquals(new Point(3, 2), locations.get(0));
        assertEquals(new Point(4, 2), locations.get(1));
        assertEquals(new Point(4, 3), locations.get(2));
        assertEquals(new Point(4, 4), locations.get(3));
        assertEquals(new Point(3, 4), locations.get(4));
        assertEquals(new Point(2, 3), locations.get(5));

        // from round 2
        assertEquals(new Point(2, 1), locations.get(6));
        assertEquals(new Point(3, 1), locations.get(7));
        assertEquals(new Point(4, 1), locations.get(8));
        assertEquals(new Point(5, 2), locations.get(9));
        assertEquals(new Point(5, 3), locations.get(10));
        assertEquals(new Point(5, 4), locations.get(11));
        assertEquals(new Point(4, 5), locations.get(12));
        assertEquals(new Point(3, 5), locations.get(13));
        assertEquals(new Point(2, 5), locations.get(14));
        assertEquals(new Point(2, 4), locations.get(15));
        assertEquals(new Point(1, 3), locations.get(16));
        assertEquals(new Point(2, 2), locations.get(17));

        // from round 3
        assertEquals(new Point(2, 0), locations.get(18));
        assertEquals(new Point(3, 0), locations.get(19));
        assertEquals(new Point(4, 0), locations.get(20));
        assertEquals(new Point(5, 0), locations.get(21));
        assertEquals(new Point(5, 1), locations.get(22));
        assertEquals(new Point(6, 2), locations.get(23));
        assertEquals(new Point(6, 3), locations.get(24));
        assertEquals(new Point(6, 4), locations.get(25));
        assertEquals(new Point(5, 5), locations.get(26));
        assertEquals(new Point(5, 6), locations.get(27));
        assertEquals(new Point(4, 6), locations.get(28));
        assertEquals(new Point(3, 6), locations.get(29));
        assertEquals(new Point(2, 6), locations.get(30));
        assertEquals(new Point(1, 5), locations.get(31));
        assertEquals(new Point(1, 4), locations.get(32));
        assertEquals(new Point(0, 3), locations.get(33));
        assertEquals(new Point(1, 2), locations.get(34));
        assertEquals(new Point(1, 1), locations.get(35));
    }

    @Test
    public void testIterator() {
        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        Iterator<AbstractTile> tiles = map.iterator();
        for (int y = 0; y < map.getHeight(); y ++) {
            for (int x = 0; x < map.getWidth(); x ++) {
                assertEquals(map.getTile(x, y), tiles.next());
            }
        }

        assertFalse(tiles.hasNext());
    }

    @Test
    public void testParallelIterators() throws InterruptedException {
        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        ExecutorService executors = Executors.newFixedThreadPool(16);
        executors.submit(() -> {
            int n = 0;
            for (AbstractTile tile : map) n++;
            assertEquals(map.getWidth() * map.getHeight(), n);
        });
        executors.shutdown();
        executors.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testStream() {
        AtomicInteger n = new AtomicInteger(0);

        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        map.tiles().forEach(e -> n.incrementAndGet());
        assertEquals(map.getWidth() * map.getHeight(), n.get());
    }
}
