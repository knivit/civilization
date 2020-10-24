package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.lake.Lake;
import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.web.render.tile.base.*;
import com.tsoft.civilization.web.render.tile.feature.*;
import com.tsoft.civilization.web.render.unit.ArchersRender;
import com.tsoft.civilization.web.render.unit.SettlersRender;
import com.tsoft.civilization.web.render.unit.WarriorsRender;
import com.tsoft.civilization.web.render.unit.WorkersRender;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@Slf4j
public class MapRender {

    private static final Map<Class<?>, Render<?>> TILE_RENDERS = new HashMap<>();
    static {
        TILE_RENDERS.put(Desert.class, new DesertRender());
        TILE_RENDERS.put(Grassland.class, new GrasslandRender());
        TILE_RENDERS.put(Lake.class, new LakeRender());
        TILE_RENDERS.put(Ocean.class, new OceanRender());
        TILE_RENDERS.put(Plain.class, new PlainRender());
        TILE_RENDERS.put(Snow.class, new SnowRender());
        TILE_RENDERS.put(Tundra.class, new TundraRender());
    }

    private static final Map<Class<?>, Render<? extends TerrainFeature>> TERRAIN_FEATURE_RENDERS = new HashMap<>();
    static {
        TERRAIN_FEATURE_RENDERS.put(Coast.class, new CoastRender());
        TERRAIN_FEATURE_RENDERS.put(Forest.class, new ForestRender());
        TERRAIN_FEATURE_RENDERS.put(Hill.class, new HillRender());
        TERRAIN_FEATURE_RENDERS.put(Ice.class, new IceRender());
        TERRAIN_FEATURE_RENDERS.put(Mountain.class, new MountainRender());
    }

    private static final Map<Class<?>, Render<? extends AbstractUnit>> UNIT_RENDERS = new HashMap<>();
    static {
        UNIT_RENDERS.put(Archers.class, new ArchersRender());
        UNIT_RENDERS.put(Settlers.class, new SettlersRender());
        UNIT_RENDERS.put(Warriors.class, new WarriorsRender());
        UNIT_RENDERS.put(Workers.class, new WorkersRender());
    }

    public String createSvg(World world) {
        String outputFileName = "World_" + world.getName() + ".png";
        TilesMap map = world.getTilesMap();

        RenderContext context = RenderContext.builder()
            .tileWidth(60)
            .tileHeight(50)
            .build();

        BufferedImage img = new BufferedImage(map.getWidth() * context.getTileWidth(), map.getHeight() * context.getTileHeight(), TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        drawMap(context, g, map);
        drawUnits(context, g, world.getCivilizations());
        //drawCities(drawArea);
        drawCivilizationsBoundaries(context, g, world.getCivilizations());

        saveImageTiFile(img, outputFileName);

        return outputFileName;
    }

    private void drawMap(RenderContext context, Graphics g, TilesMap map) {
        drawTiles(context, g, map);
    }

    private void drawTiles(RenderContext context, Graphics g, TilesMap map) {
        for (int y = 0; y < map.getHeight(); y ++) {
            for (int x = 0; x < map.getWidth(); x ++) {
                drawTile(context, g, x, y, map.getTile(x, y));
            }
        }
    }

    private void drawTile(RenderContext context, Graphics g, int x, int y, AbstractTile tile) {
        Render render = TILE_RENDERS.get(tile.getClass());
        if (render == null) {
            throw new IllegalArgumentException("No render for class = " + tile.getClass().getName());
        }

        render.render(context, g, x, y, tile);
    }

    private void drawCivilizationsBoundaries(RenderContext context, Graphics g, CivilizationList civilizations) {
        civilizations.forEach(c -> drawCivilizationBoundary(context, g, c));
    }

    private void drawCivilizationBoundary(RenderContext context, Graphics g, Civilization civilization) {
        Collection<com.tsoft.civilization.util.Point> points = civilization.territory().getCivilizationLocations();
    }

    private void drawUnits(RenderContext context, Graphics g, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.units().stream())
            .forEach(u -> drawUnit(context, g, u.getLocation().getX(), u.getLocation().getY(), u));
    }

    private void drawUnit(RenderContext context, Graphics g, int x, int y, AbstractUnit unit) {
        Render render = UNIT_RENDERS.get(unit.getClass());
        if (render == null) {
            throw new IllegalArgumentException("No render for class = " + unit.getClass().getName());
        }

        render.render(context, g, x, y, unit);
    }

    private void saveImageTiFile(BufferedImage img, String outputFileName) {
        try {
            ImageIO.write(img, "png", new File(outputFileName));
            log.info("File {} generated", outputFileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
