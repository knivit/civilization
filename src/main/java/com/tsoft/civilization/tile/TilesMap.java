package com.tsoft.civilization.tile;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.util.dir.AbstractDir;
import com.tsoft.civilization.util.dir.Dir6;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.MapConfiguration;
import com.tsoft.civilization.world.MapSize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tsoft.civilization.world.MapSize.CUSTOM;

@Slf4j
public class TilesMap implements Iterable<AbstractTerrain> {

    public static int MIN_WIDTH = 2;
    public static int MAX_WIDTH = 1000;
    public static int MIN_HEIGHT = 2;
    public static int MAX_HEIGHT = 1000;

    private final AbstractTerrain[][] tiles;

    @Getter
    private final MapSize mapSize;

    @Getter @Setter
    private MapConfiguration mapConfiguration;

    private final int width;
    private final int height;

    public TilesMap(MapSize mapSize) {
        this(mapSize, mapSize.getWidth(), mapSize.getHeight());
    }

    public TilesMap(int width, int height) {
        this(CUSTOM, width, height);
    }

    public TilesMap(MapSize mapSize, int width, int height) {
        this.mapSize = mapSize;

        // For 'Six Tiles' map the number of rows must be even
        if ((height % 2) != 0) {
            height ++;
        }

        assert (width >= MIN_WIDTH && width <= MAX_WIDTH) : "Width = " + width + " is not in range [2..1000]";
        assert (height >= MIN_HEIGHT && height <= MAX_HEIGHT) : "Height = " + height + " is not in range [2..1000]";

        this.width = width;
        this.height = height;

        tiles = new AbstractTerrain[width][height];
        log.debug("Tiles map {} ({}x{}) created", mapSize, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public AbstractTerrain getTile(int x, int y) {
        assert (x >= 0 && x < getWidth()) : "x = " + x + " is not in range [0.." + (width - 1) + "]";
        assert (y >= 0 && y < getHeight()) : "y = " + y + " is not in range [0.." + (height - 1) + "]";

        return tiles[x][y];
    }

    public AbstractTerrain getTile(Point location) {
        return getTile(location.getX(), location.getY());
    }

    public void setTile(Point location, AbstractTerrain tile) {
        assert (location.getX() >= 0 && location.getX() < getWidth()) : "Location = " + location + " is not in range [0.." + (width - 1) + "][0.." + (width - 1) + "]";
        assert (location.getY() >= 0 && location.getY() < getHeight()) : "Location = " + location + " is not in range [0.." + (height - 1) + "][0.." + (height - 1) + "]";
        assert (tile != null) : "Tile must be not null";

        tiles[location.getX()][location.getY()] = tile;
        tile.setLocation(location);
    }

    public Point getLocation(int x, int y) {
        return getTile(x, y).getLocation();
    }

    @Override
    public Iterator<AbstractTerrain> iterator() {
        return new Iterator<>() {
            private int n = 0;

            @Override
            public boolean hasNext() {
                return n < (width * height);
            }

            @Override
            public AbstractTerrain next() {
                int x = n % width;
                int y = n / width;
                n ++;
                return tiles[x][y];
            }
        };
    }

    public Stream<AbstractTerrain> tiles() {
        return Stream.of(tiles).flatMap(Stream::of);
    }

    // The map is "cyclic", i.e. after a last tile
    // in a row goes a first tile in this row and
    // after a last tile in a column goes a first tile in this column
    public Point addDirToLocation(Point location, AbstractDir dir) {
        int x = getXPlusDX(location.getX(), dir.getDX());
        int y = getYPlusDY(location.getY(), dir.getDY());
        return tiles[x][y].getLocation();
    }

    public AbstractDir getDirToLocation(Point from, Point to) {
        List<Dir6> dirs = Dir6.staticGetDirs(from.getY());
        for (Dir6 dir : dirs) {
            int x = getXPlusDX(from.getX(), dir.getDX());
            int y = getYPlusDY(from.getY(), dir.getDY());
            if ((x == to.getX()) && (y == to.getY())) {
                return dir;
            }
        }
        return null;
    }

    private int getXPlusDX(int x, int dx) {
        x += dx;
        if (x < 0) x += width;
        else if (x > (width - 1)) x -= width;
        return x;
    }

    private int getYPlusDY(int y, int dy) {
        y += dy;
        if (y < 0) y += height;
        else if (y > (height - 1)) y -= height;
        return y;
    }

    public boolean isTilesNearby(Point loc1, Point loc2) {
        return isTilesNearby(loc1, loc2, 1);
    }

    public boolean isTilesNearby(Point loc1, Point loc2, int radius) {
        Set<Point> around = getLocationsAround(loc1, radius);
        return around.contains(loc2);
    }

    public List<AbstractTerrain> getTilesAround(Point location, int radius) {
        return getLocationsAround(location, radius).stream()
            .map(this::getTile)
            .collect(Collectors.toList());
    }

    // Get the locations around the given location within the radius
    // The point itself doesn't included
    public Set<Point> getLocationsAround(Point location, int radius) {
        Set<Point> locations = new HashSet<>();
        if (radius == 0) {
            return locations;
        }

        // first round
        // define the corners, their order must be clockwise, i.e. for SIX_TILES
        // (Left, Up) -> (Right, Up) -> (Right, Zero) -> (Right, Down) -> (Left, Down) -> (Left, Zero)
        ArrayList<? extends AbstractDir> dirs = new Dir6(-1, 0).getDirs(location.getY());
        ArrayList<Point> corners = new ArrayList<>();
        for (AbstractDir dir : dirs) {
            Point cornerLocation = addDirToLocation(location, dir);
            corners.add(cornerLocation);
            locations.add(cornerLocation);
        }

        // other rounds
        for (int r = 2; r <= radius; r ++) {
            // expand the corners further
            dirs = new Dir6(-1, 0).getDirs(location.getY() - r + 1);
            for (int i = 0; i < corners.size(); i ++) {
                Point newCorner = addDirToLocation(corners.get(i), dirs.get(i));
                corners.set(i, newCorner);
            }

            // draw lines between corners and get tiles from that lines
            for (int i = 0; i < corners.size(); i ++) {
                int toCorner = (i == corners.size() - 1) ? 0 : i + 1;
                List<Point> locationsOnLine = getLocationsOnLine(corners.get(i), corners.get(toCorner), i);
                locations.addAll(locationsOnLine);
            }
        }

        return locations;
    }

    /* Return a border with the given width around the given territory */
    public Set<Point> getLocationsAroundTerritory(Set<Point> territory, int width) {
        if (width < 0) {
            throw new IllegalArgumentException("Invalid width = " + width + ", must be >= 0");
        }

        if (territory == null || territory.isEmpty() || width == 0) {
            return Collections.emptySet();
        }

        Set<Point> result = new HashSet<>();
        for (Point loc : territory) {
            Set<Point> around = getLocationsAround(loc, width);
            result.addAll(around);
        }

        result.removeAll(territory);

        return result;
    }

    // End point doesn't include
    // sideNo - number of hexagon's side (0 - Up, 1 - (Right, Up) etc clockwise)
    private List<Point> getLocationsOnLine(Point a, Point b, int sideNo) {
        assert (sideNo >= 0 && sideNo <= 5) : "sideNo = " + sideNo + ", but must be in [0..5]";

        ArrayList<Point> locations = new ArrayList<>();

        // horizontal
        Dir6[] leftUpToRightUp = new Dir6[] { new Dir6(1, 0) };
        Dir6[] rightDownToLeftDown = new Dir6[] { new Dir6(-1, 0) };

        // diagonal
        Dir6[] rightUpToRightZero = new Dir6[] { new Dir6(0, 1), new Dir6(1, 1) };
        Dir6[] rightZeroToRightDown = new Dir6[] { new Dir6(-1, 1), new Dir6(0, 1) };
        Dir6[] leftDownToLeftZero = new Dir6[] { new Dir6(-1, -1), new Dir6(0, -1) };
        Dir6[] leftZeroToLeftUp = new Dir6[] { new Dir6(0, -1), new Dir6(1, -1) };

        Dir6[][] allDirs = new Dir6[][] {
            leftUpToRightUp, rightUpToRightZero, rightZeroToRightDown,
            rightDownToLeftDown, leftDownToLeftZero, leftZeroToLeftUp
        };

        Dir6[] dirs = allDirs[sideNo];
        if (sideNo == 0 || sideNo == 3) {
            for (Point loc = new Point(a); !loc.equals(b); loc = addDirToLocation(loc, dirs[0])) {
                locations.add(loc);
            }
            return locations;
        }

        int index = 0;
        if ((a.getY() % 2) == 0) {
            index = 1;
        }
        for (Point loc = new Point(a); !loc.equals(b); loc = addDirToLocation(loc, dirs[index])) {
            locations.add(loc);

            index ++;
            if (index >= dirs.length) {
                index = 0;
            }
        }
        return locations;
    }

    public List<Point> getTileClassLocations(Class<? extends AbstractTerrain> tileClass) {
        Objects.requireNonNull(tileClass, "Tile class must be not null");

        return tiles()
            .filter(t -> t.getClass().equals(tileClass))
            .map(AbstractTerrain::getLocation)
            .collect(Collectors.toList());
    }

    public List<Point> getTerrainFeatureClassLocations(Class<? extends AbstractFeature> featureClass) {
        Objects.requireNonNull(featureClass, "TerrainFeature class must be not null");

        return tiles()
            .filter(t -> t.hasFeature(featureClass))
            .map(AbstractTerrain::getLocation)
            .collect(Collectors.toList());
    }

    /** Return null if the given coordinates not on the map */
    public Point getLocation(String col, String row) {
        if (col == null || row == null) {
            return null;
        }

        int c = NumberUtil.parseInt(col, -1);
        int r = NumberUtil.parseInt(row, -1);
        if ((c < 0 || c >= width) || (r < 0 || r >= height)) {
            return null;
        }

        return tiles[c][r].getLocation();
    }

    /** Start a new year, refresh tiles' properties */
    public void startYear() {
        tiles()
            .filter(AbstractTerrain::isOcean)
            .filter(this::isDeepOcean)
            .forEach(t -> ((Ocean) t).setDeepOcean(true));
    }

    // If as far as 3 tiles around is ocean, then this tile is deep ocean
    private boolean isDeepOcean(AbstractTerrain tile) {
        return getTilesAround(tile.getLocation(), 2).stream()
            .anyMatch(t -> !t.isOcean());
    }
}
