package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.render.city.CityRender;
import com.tsoft.civilization.web.render.civilization.BorderRender;
import com.tsoft.civilization.web.render.civilization.CivilizationRender;
import com.tsoft.civilization.web.render.unit.UnitRenderCatalog;
import com.tsoft.civilization.world.World;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

public class ImageRender {

    private static final int TILE_WIDTH_PX = 120;
    private static final int TILE_HEIGHT_PX = 120;

    private final CivilizationRender civilizationRender = new CivilizationRender();
    private final CityRender cityRender = new CityRender();
    private final RenderCatalog<AbstractUnit> unitRenderCatalog = new UnitRenderCatalog();

    public void createPng(World world, Path outputImageFileName) {
        TilesMap map = world.getTilesMap();
        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), TILE_WIDTH_PX, TILE_HEIGHT_PX);
        GraphicsContext graphicsContext = new GraphicsContext((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY()).build();

        MapRender mapRender = new MapRender();
        mapRender.drawMap(renderContext, graphicsContext, map);
        drawUnits(renderContext, graphicsContext, world.getCivilizations());
        drawCities(renderContext, graphicsContext, world.getCivilizations());
        drawCivilizationsBoundaries(renderContext, graphicsContext, map, world.getCivilizations());

        graphicsContext.saveImageToFile(outputImageFileName);
    }

    private void drawCivilizationsBoundaries(RenderContext renderContext, GraphicsContext graphicsContext, TilesMap map, CivilizationList civilizations) {
        BorderRender borderRender = new BorderRender();

        boolean[] sides = new boolean[6];
        for (AbstractTerrain tile : map) {
            Civilization civ = civilizations.getCivilizationOnTile(tile.getLocation());
            if (civ == null) {
                continue;
            }

            int side = 0;
            List<AbstractTerrain> tilesAround = map.getTilesAround(tile.getLocation(), 1);
            for (AbstractTerrain neighborTile : tilesAround) {
                Civilization neighborCiv = civilizations.getCivilizationOnTile(neighborTile.getLocation());
                sides[side] = (neighborCiv == null) || !neighborCiv.equals(civ);
            }

            Color color = civilizationRender.getColor(civ);
            List<RenderTileInfo> infos = renderContext.getTileInfo(tile.getLocation());
            for (RenderTileInfo tileInfo : infos) {
                borderRender.outline(renderContext, graphicsContext, tileInfo, sides, color);
            }
        }
    }

    private void drawUnits(RenderContext renderContext, GraphicsContext graphics, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.getUnitService().stream())
            .forEach(u -> {
                List<RenderTileInfo> infos = renderContext.getTileInfo(u.getLocation());
                drawUnit(renderContext, graphics, infos, u);
            });
    }

    private void drawUnit(RenderContext context, GraphicsContext graphics, List<RenderTileInfo> infos, AbstractUnit unit) {
        if (infos != null) {
            for (RenderTileInfo tileInfo : infos) {
                unitRenderCatalog.render(context, graphics, tileInfo, unit);
            }
        }
    }

    private void drawCities(RenderContext renderContext, GraphicsContext graphics, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.getCityService().stream())
            .forEach(c -> {
                List<RenderTileInfo> infos = renderContext.getTileInfo(c.getLocation());
                drawCity(renderContext, graphics, infos, c);
            });
    }

    private void drawCity(RenderContext context, GraphicsContext graphics, List<RenderTileInfo> infos, City city) {
        if (infos != null) {
            for (RenderTileInfo tileInfo : infos) {
                cityRender.render(context, graphics, tileInfo, city);
            }
        }
    }
}
