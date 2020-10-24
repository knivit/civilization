package com.tsoft.civilization.web.render;

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
import com.tsoft.civilization.web.render.tile.base.*;
import com.tsoft.civilization.web.render.tile.feature.*;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private final Class<?> clazz;
    private int n;

    public MapRender(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void createSvg(TilesMap map) {
        String path = "target/" + clazz.getPackageName().replace('.', '/') + "/" + clazz.getSimpleName();
        String outputFileName = path + "/" + (++ n) + "_map_" + map.getWidth() + "x" + map.getHeight() + ".png";

        try {
            Files.deleteIfExists(Path.of(outputFileName));
            Files.createDirectories(Path.of(outputFileName));
        } catch (Exception e) {
            throw new IllegalStateException("Can't create file " + outputFileName, e);
        }

        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), 60, 50);
        RenderArea renderArea = RenderArea.build(renderContext);

        BufferedImage img = new BufferedImage((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY(), TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        drawTiles(renderContext, renderArea, g, map);
        saveImageTiFile(img, outputFileName);
    }

    public void drawTiles(RenderContext renderContext, RenderArea renderArea, Graphics g, TilesMap map) {
        for (RenderArea.RenderInfo renderInfo : renderArea.getRenderInfo()) {
            AbstractTile tile = map.getTile(renderInfo.col, renderInfo.row);
            drawTile(renderContext, g, renderInfo.x, renderInfo.y, tile);
        }
    }

    private void drawTile(RenderContext renderContext, Graphics g, int x, int y, AbstractTile tile) {
        Render render = TILE_RENDERS.get(tile.getClass());
        if (render == null) {
            throw new IllegalArgumentException("No render for class = " + tile.getClass().getName());
        }

        render.render(renderContext, g, x, y, tile);
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
