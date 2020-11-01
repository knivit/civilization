package com.tsoft.civilization.web.render;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.render.tile.TerrainFeatureRenderCatalog;
import com.tsoft.civilization.web.render.tile.TileRenderCatalog;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class MapRender {

    private final Class<?> clazz;
    private int n;

    private final RenderCatalog<AbstractTile> tileRenderCatalog = new TileRenderCatalog();
    private final RenderCatalog<TerrainFeature> terrainFeatureRenderCatalog = new TerrainFeatureRenderCatalog();

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

        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), 120, 120);
        GraphicsContext graphicsContext = new GraphicsContext((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY()).build();

        drawTiles(renderContext, graphicsContext, map);
        graphicsContext.saveImageToFile(outputFileName);
    }

    public void drawTiles(RenderContext renderContext, GraphicsContext graphicsContext, TilesMap map) {
        for (RenderContext.RenderInfo renderInfo : renderContext.getRenderInfo()) {
            AbstractTile tile = map.getTile(renderInfo.col, renderInfo.row);
            drawTile(renderContext, graphicsContext, renderInfo.x, renderInfo.y, tile);
        }
    }

    private void drawTile(RenderContext renderContext, GraphicsContext graphicsContext, int x, int y, AbstractTile tile) {
        tileRenderCatalog.render(renderContext, graphicsContext, x, y, tile);
        drawTerrainFeatures(renderContext, graphicsContext, x, y, tile);
    }

    private void drawTerrainFeatures(RenderContext renderContext, GraphicsContext graphicsContext, int x, int y, AbstractTile tile) {
        for (TerrainFeature feature : tile.getTerrainFeatures()) {
            drawTerrainFeature(renderContext, graphicsContext, x, y, feature);
        }
    }

    private void drawTerrainFeature(RenderContext renderContext, GraphicsContext graphicsContext, int x, int y, TerrainFeature feature) {
        terrainFeatureRenderCatalog.render(renderContext, graphicsContext, x, y, feature);
    }
}
