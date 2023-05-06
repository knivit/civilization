package com.tsoft.civilization.tile;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TilesMapTest {

    @Test
    void get_tiles_around_1() {
        TilesMap map = MockTilesMap.of(
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
        Set<Point> locations = map.getLocationsAround(new Point(3, 3), 1);

        assertThat(locations)
            .hasSize(6)
            .containsExactlyInAnyOrder(
                new Point(3, 2), new Point(4, 2), new Point(4, 3),
                new Point(4, 4), new Point(3, 4), new Point(2, 3)
            );
    }

    @Test
    void get_tiles_around_2() {
        TilesMap map = MockTilesMap.of(
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
        Set<Point> locations = map.getLocationsAround(new Point(3, 3), 2);

        assertThat(locations)
            .hasSize(6 + 12)
            .containsExactlyInAnyOrder(
                // from round 1
                new Point(3, 2), new Point(4, 2), new Point(4, 3),
                new Point(4, 4), new Point(3, 4), new Point(2, 3),

                // from round 2
                new Point(2, 1), new Point(3, 1), new Point(4, 1),
                new Point(5, 2), new Point(5, 3), new Point(5, 4),
                new Point(4, 5), new Point(3, 5), new Point(2, 5),
                new Point(2, 4), new Point(1, 3), new Point(2, 2)
            );
    }

    @Test
    void get_tiles_around_3() {
        TilesMap map = MockTilesMap.of(
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
        Set<Point> locations = map.getLocationsAround(new Point(3, 3), 3);

        assertThat(locations)
            .hasSize(6 + 12 + 18)
            .containsExactlyInAnyOrder(
                // from round 1
                new Point(3, 2), new Point(4, 2), new Point(4, 3),
                new Point(4, 4), new Point(3, 4), new Point(2, 3),

                // from round 2
                new Point(2, 1), new Point(3, 1), new Point(4, 1),
                new Point(5, 2), new Point(5, 3), new Point(5, 4),
                new Point(4, 5), new Point(3, 5), new Point(2, 5),
                new Point(2, 4), new Point(1, 3), new Point(2, 2),

                // from round 3
                new Point(2, 0), new Point(3, 0), new Point(4, 0),
                new Point(5, 0), new Point(5, 1), new Point(6, 2),
                new Point(6, 3), new Point(6, 4), new Point(5, 5),
                new Point(5, 6), new Point(4, 6), new Point(3, 6),
                new Point(2, 6), new Point(1, 5), new Point(1, 4),
                new Point(0, 3), new Point(1, 2), new Point(1, 1)
            );
    }

    @Test
    void get_locations_around_territory_1() {
        TilesMap tilesMap = MockTilesMap.of(
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

        assertThat(tilesMap.getLocationsAroundTerritory(Set.of(new Point(0, 0)), 1))
            .containsExactlyInAnyOrder(
                new Point(7, 0), new Point(7, 7), new Point(0, 7), new Point(1, 0), new Point(0, 1), new Point(7, 1)
            );

        assertThat(tilesMap.getLocationsAroundTerritory(Set.of(new Point(1, 1)), 1))
            .containsExactlyInAnyOrder(
                new Point(0, 1), new Point(1, 0), new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(1, 2)
            );

        assertThat(tilesMap.getLocationsAroundTerritory(Set.of(new Point(1, 1), new Point(3, 3)), 1))
            .containsExactlyInAnyOrder(
                new Point(0, 1), new Point(1, 0), new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(1, 2),
                new Point(2, 3), new Point(3, 2), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(3, 4)
            );

        assertThat(tilesMap.getLocationsAroundTerritory(Set.of(new Point(1, 1), new Point(2, 1)), 1))
            .containsExactlyInAnyOrder(
                new Point(0, 1), new Point(1, 0), new Point(2, 0), new Point(2, 2), new Point(1, 2),
                new Point(3, 0), new Point(3, 1), new Point(3, 2)
            );
    }

    @Test
    void test_iterator() {
        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        Iterator<AbstractTerrain> tiles = map.iterator();
        for (int y = 0; y < map.getHeight(); y ++) {
            for (int x = 0; x < map.getWidth(); x ++) {
                assertEquals(map.getTile(x, y), tiles.next());
            }
        }

        assertThat(tiles.hasNext()).isFalse();
    }

    @Test
    void test_parallel_iterators() throws InterruptedException {
        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        ExecutorService executors = Executors.newFixedThreadPool(16);

        executors.submit(() -> {
            int n = 0;
            for (AbstractTerrain tile : map) {
                log.debug("Found tile {}", tile.getLocation());
                n ++;
            }
            assertEquals(map.getWidth() * map.getHeight(), n);
        });

        executors.shutdown();

        assertThat(executors.awaitTermination(10, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void test_stream() {
        AtomicInteger n = new AtomicInteger(0);

        TilesMap map = MockWorld.SIMPLE_TILES_MAP;

        map.tiles().forEach(e -> n.incrementAndGet());
        assertEquals(map.getWidth() * map.getHeight(), n.get());
    }
}
