package com.tsoft.civilization.web.render;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.render.tile.HexagonRender;
import com.tsoft.civilization.web.render.tile.TerrainFeatureRenderCatalog;
import com.tsoft.civilization.web.render.tile.TileRenderCatalog;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class MapRender {

    private final Class<?> clazz;
    private int n;

    private final RenderCatalog<AbstractTile> tileRenderCatalog = new TileRenderCatalog();
    private final RenderCatalog<TerrainFeature> terrainFeatureRenderCatalog = new TerrainFeatureRenderCatalog();

    public MapRender(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void createPng(TilesMap map) {
        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), 120, 120);
        GraphicsContext graphicsContext = new GraphicsContext((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY()).build();

        drawTiles(renderContext, graphicsContext, map);
        outlineTiles(renderContext, graphicsContext, map);

        String path = "target/" + clazz.getPackageName().replace('.', '/') + "/" + clazz.getSimpleName();
        String outputFileName = path + "/" + (++ n) + "_map_" + map.getWidth() + "x" + map.getHeight() + ".png";
        graphicsContext.saveImageToFile(outputFileName);
    }

    public void drawTiles(RenderContext renderContext, GraphicsContext graphicsContext, TilesMap map) {
        for (RenderTileInfo tileInfo : renderContext.getTilesInfo()) {
            AbstractTile tile = map.getTile(tileInfo.col, tileInfo.row);
            drawTile(renderContext, graphicsContext, tileInfo, tile);
        }
    }

    private void drawTile(RenderContext renderContext, GraphicsContext graphicsContext, RenderTileInfo tileInfo, AbstractTile tile) {
        tileRenderCatalog.render(renderContext, graphicsContext, tileInfo, tile);
        drawTerrainFeatures(renderContext, graphicsContext, tileInfo, tile);
    }

    private void drawTerrainFeatures(RenderContext renderContext, GraphicsContext graphicsContext, RenderTileInfo tileInfo, AbstractTile tile) {
        for (TerrainFeature feature : tile.getTerrainFeatures()) {
            drawTerrainFeature(renderContext, graphicsContext, tileInfo, feature);
        }
    }

    private void drawTerrainFeature(RenderContext renderContext, GraphicsContext graphicsContext, RenderTileInfo tileInfo, TerrainFeature feature) {
        terrainFeatureRenderCatalog.render(renderContext, graphicsContext, tileInfo, feature);
    }

    public void outlineTiles(RenderContext renderContext, GraphicsContext graphicsContext, TilesMap map) {
        HexagonRender hexagonRender = new HexagonRender();

        for (RenderTileInfo tileInfo : renderContext.getTilesInfo()) {
            AbstractTile tile = map.getTile(tileInfo.col, tileInfo.row);
            hexagonRender.outline(renderContext, graphicsContext, tileInfo, Color.WHITE);
        }
    }
}
