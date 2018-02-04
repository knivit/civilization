package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.Coast;
import com.tsoft.civilization.tile.base.Desert;
import com.tsoft.civilization.tile.base.Grassland;
import com.tsoft.civilization.tile.base.Ice;
import com.tsoft.civilization.tile.base.Lake;
import com.tsoft.civilization.tile.base.Mountain;
import com.tsoft.civilization.tile.base.Ocean;
import com.tsoft.civilization.tile.base.Plain;
import com.tsoft.civilization.tile.base.Snow;
import com.tsoft.civilization.tile.base.Tundra;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.Atoll;
import com.tsoft.civilization.tile.feature.Fallout;
import com.tsoft.civilization.tile.feature.FloodPlain;
import com.tsoft.civilization.tile.feature.Forest;
import com.tsoft.civilization.tile.feature.Hill;
import com.tsoft.civilization.tile.feature.Jungle;
import com.tsoft.civilization.tile.feature.Marsh;
import com.tsoft.civilization.tile.feature.Oasis;

import java.util.ArrayList;
import java.util.List;

public class TileCatalog {
    private static final ArrayList<AbstractTile> tilesCatalog = new ArrayList<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        tilesCatalog.add(new Coast());
        tilesCatalog.add(new Desert());
        tilesCatalog.add(new Grassland());
        tilesCatalog.add(new Ice());
        tilesCatalog.add(new Lake());
        tilesCatalog.add(new Mountain());
        tilesCatalog.add(new Ocean());
        tilesCatalog.add(new Plain());
        tilesCatalog.add(new Snow());
        tilesCatalog.add(new Tundra());
    }

    private static final List<TerrainFeature> featuresCatalog = new ArrayList<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        featuresCatalog.add(new Atoll());
        featuresCatalog.add(new Fallout());
        featuresCatalog.add(new FloodPlain());
        featuresCatalog.add(new Forest());
        featuresCatalog.add(new Hill());
        featuresCatalog.add(new Jungle());
        featuresCatalog.add(new Marsh());
        featuresCatalog.add(new Oasis());
    }

    private TileCatalog() { }

    public static List<AbstractTile> baseTiles() {
        return tilesCatalog;
    }

    public static List<TerrainFeature> features() {
        return featuresCatalog;
    }
}
