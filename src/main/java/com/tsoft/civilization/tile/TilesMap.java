package com.tsoft.civilization.tile;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Dir6;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.List;

public class TilesMap {
    private AbstractTile[][] tiles;
    private MapType mapType;

    private int width;
    private int height;

    public TilesMap(MapType mapType, int width, int height) {
        assert (width >= 2 && width <= 1000) : "Width = " + width + " is not in range [2..1000]";
        assert (height >= 2 && height <= 1000) : "Height = " + height + " is not in range [2..1000]";
        if (mapType == MapType.SIX_TILES) {
            assert ((height % 2) == 0) : "For 'Six Tiles' map the number of rows must be even";
        }

        this.mapType = mapType;
        this.width = width;
        this.height = height;
        tiles = new AbstractTile[width][height];
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public AbstractTile getTile(int x, int y) {
        assert (x >= 0 && x < getWidth()) : "x = " + x + " is not in range [0.." + (getWidth() - 1) + "]";
        assert (y >= 0 && y < getHeight()) : "y = " + y + " is not in range [0.." + (getHeight() - 1) + "]";

        return tiles[x][y];
    }

    public AbstractTile getTile(Point location) {
        return getTile(location.getX(), location.getY());
    }

    public void setTile(Point location, AbstractTile tile) {
        assert (location.getX() >= 0 && location.getX() < getWidth()) : "Location = " + location + " is not in range [0.." + (getWidth() - 1) + "][0.." + (getHeight() - 1) + "]";
        assert (location.getY() >= 0 && location.getY() < getHeight()) : "Location = " + location + " is not in range [0.." + (getWidth() - 1) + "][0.." + (getHeight() - 1) + "]";
        assert (tile != null) : "Tile must be not null";

        tiles[location.getX()][location.getY()] = tile;
        tile.setLocation(location);
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

    // The map is "cyclic", i.e. after a last tile
    // in a row goes a first tile in this row and
    // after a last tile in a column goes a first tile in this column
    public Point addDirToLocation(Point location, AbstractDir dir) {
        int x = getXPlusDX(location.getX(), dir.getDX());
        int y = getYPlusDY(location.getY(), dir.getDY());
        return new Point(x, y);
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

    // Get the locations around the given location within the radius
    // The point itself doesn't included
    public ArrayList<Point> getLocationsAround(Point location, int radius) {
        ArrayList<Point> locations = new ArrayList<Point>();
        if (radius == 0) {
            return locations;
        }

        // first round
        // define the corners, their order must be clockwise, i.e. for SIX_TILES
        // (Left, Up) -> (Right, Up) -> (Right, Zero) -> (Right, Down) -> (Left, Down) -> (Left, Zero)
        ArrayList<? extends AbstractDir> dirs = mapType.getDir().getDirs(location.getY());
        ArrayList<Point> corners = new ArrayList<Point>();
        for (AbstractDir dir : dirs) {
            Point cornerLocation = addDirToLocation(location, dir);
            corners.add(cornerLocation);
            locations.add(cornerLocation);
        }

        // other rounds
        for (int r = 2; r <= radius; r ++) {
            switch (mapType) {
                case SIX_TILES: {
                    // expand the corners further
                    dirs = mapType.getDir().getDirs(location.getY() - r + 1);
                    for (int i = 0; i < corners.size(); i ++) {
                        Point newCorner = addDirToLocation(corners.get(i), dirs.get(i));
                        corners.set(i, newCorner);
                    }

                    // draw lines between corners and get tiles from that lines
                    for (int i = 0; i < corners.size(); i ++) {
                        int toCorner = (i == corners.size() - 1) ? 0 : i + 1;
                        ArrayList<Point> locationsOnLine = getLocationsOnLine(corners.get(i), corners.get(toCorner), i);
                        locations.addAll(locationsOnLine);
                    }
                    break;
                }

                default: {
                    throw new IllegalArgumentException("Cases for other map's type don't implemented yet");
                }
            }
        }
        return locations;
    }

    // End point doesn't included
    // sideNo - number of hexagonal's side (0 - Up, 1 - (Right, Up) etc clockwise)
    private ArrayList<Point> getLocationsOnLine(Point a, Point b, int sideNo) {
        assert (sideNo >= 0 && sideNo <= 5) : "sideNo = " + sideNo + ", but must be in [0..5]";

        ArrayList<Point> locations = new ArrayList<Point>();

        // horizontal
        Dir6[] leftUpToRightUp = new Dir6[] { new Dir6(1, 0) };
        Dir6[] rightDownToLeftDown = new Dir6[] { new Dir6(-1, 0) };

        // diagonal
        Dir6[] rightUpToRightZero = new Dir6[] { new Dir6(0, 1), new Dir6(1, 1) };
        Dir6[] rightZeroToRightDown = new Dir6[] { new Dir6(-1, 1), new Dir6(0, 1) };
        Dir6[] leftDownToLeftZero = new Dir6[] { new Dir6(-1, -1), new Dir6(0, -1) };
        Dir6[] leftZeroToLeftUp = new Dir6[] { new Dir6(0, -1), new Dir6(1, -1) };

        Dir6[][] allDirs = new Dir6[][] { leftUpToRightUp, rightUpToRightZero, rightZeroToRightDown, rightDownToLeftDown, leftDownToLeftZero, leftZeroToLeftUp };

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

    public ArrayList<Point> getTileClassLocations(Class<? extends AbstractTile> tileClass) {
        assert (tileClass != null) : "Tile Class must be not null";

        ArrayList<Point> points = new ArrayList<>();
        for (int y = 0; y < getHeight(); y ++) {
            for (int x = 0; x < getWidth(); x ++) {
                AbstractTile tile = getTile(x, y);
                if (tileClass.equals(tile.getClass())) {
                    Point point = new Point(x, y);
                    points.add(point);
                }
            }
        }
        return points;
    }

    /** Return null if the given coordinates not on the map */
    public Point getLocation(String col, String row) {
        int c = NumberUtil.parseInt(col, -1);
        int r = NumberUtil.parseInt(row, -1);
        if ((c < 0 || c >= width) || (r < 0 || r >= height)) {
            return null;
        }

        return new Point(c, r);
    }

    public List<Point> getTilesToStartCivilization() {
        ArrayList<Point> points = new ArrayList<>();
        for (int y = 0; y < getHeight(); y ++) {
            for (int x = 0; x < getWidth(); x ++) {
                AbstractTile tile = getTile(x, y);
                if (tile.canBuildCity()) {
                    Point point = new Point(x, y);
                    points.add(point);
                }
            }
        }
        return points;
    }
}
