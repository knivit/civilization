package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.render.civilization.BorderRender;
import com.tsoft.civilization.web.render.civilization.CivilizationRender;
import com.tsoft.civilization.web.render.city.CityRender;
import com.tsoft.civilization.web.render.unit.UnitRenderCatalog;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class WorldRender {

    private static final int TILE_WIDTH_PX = 120;
    private static final int TILE_HEIGHT_PX = 120;

    private final RenderFileNameGenerator fileNameGenerator;
    private final CivilizationRender civilizationRender = new CivilizationRender();
    private final CityRender cityRender = new CityRender();
    private final RenderCatalog<AbstractUnit> unitRenderCatalog = new UnitRenderCatalog();

    public WorldRender(Class<?> clazz) {
        fileNameGenerator = new RenderFileNameGenerator(clazz, "world");
    }

    public void createHtml(World world, Civilization activeCivilization) {
        Civilization currentCivilization = Sessions.getCurrent().getCivilization();
        try {
            Sessions.getCurrent().setActiveCivilization(activeCivilization);

            Path imageFileName = createPng(world);

            StatusContext statusContext = new StatusContext();
            StatusRender statusRender = new StatusRender();
            statusContext.setImage(imageFileName);
            statusRender.render(statusContext, world);
            statusContext.saveHtmlToFile(fileNameGenerator.getOutputFileName(".html"));
        } finally {
            Sessions.getCurrent().setActiveCivilization(currentCivilization);
        }
    }

    public Path createPng(World world) {
        TilesMap map = world.getTilesMap();
        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), TILE_WIDTH_PX, TILE_HEIGHT_PX);
        GraphicsContext graphicsContext = new GraphicsContext((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY()).build();

        MapRender mapRender = new MapRender();
        mapRender.drawMap(renderContext, graphicsContext, map);
        drawUnits(renderContext, graphicsContext, world.getCivilizations());
        drawCities(renderContext, graphicsContext, world.getCivilizations());
        drawCivilizationsBoundaries(renderContext, graphicsContext, map, world.getCivilizations());

        Path generatedFileName = fileNameGenerator.getOutputFileName(".png");
        graphicsContext.saveImageToFile(generatedFileName);
        return generatedFileName;
    }

    private void drawCivilizationsBoundaries(RenderContext renderContext, GraphicsContext graphicsContext, TilesMap map, CivilizationList civilizations) {
        BorderRender borderRender = new BorderRender();

        boolean[] sides = new boolean[6];
        for (AbstractTile tile : map) {
            Civilization civ = civilizations.getCivilizationOnTile(tile.getLocation());
            if (civ == null) {
                continue;
            }

            int side = 0;
            List<AbstractTile> tilesAround = map.getTilesAround(tile.getLocation(), 1);
            for (AbstractTile neighborTile : tilesAround) {
                Civilization neighborCiv = civilizations.getCivilizationOnTile(neighborTile.getLocation());
                sides[side] = (neighborCiv == null) || !neighborCiv.equals(civ);
            }

            Color color = civilizationRender.getColor(civ);
            borderRender.outline(renderContext, graphicsContext, renderContext.getTileInfo(tile.getLocation()), sides, color);
        }
    }

    private void drawUnits(RenderContext renderContext, GraphicsContext graphics, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.units().stream())
            .forEach(u -> drawUnit(renderContext, graphics, renderContext.getTileInfo(u.getLocation()), u));
    }

    private void drawUnit(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, AbstractUnit unit) {
        if (tileInfo != null) {
            unitRenderCatalog.render(context, graphics, tileInfo, unit);
        }
    }

    private void drawCities(RenderContext renderContext, GraphicsContext graphics, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.cities().stream())
            .forEach(c -> drawCity(renderContext, graphics, renderContext.getTileInfo(c.getLocation()), c));
    }

    private void drawCity(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, City city) {
        if (tileInfo != null) {
            cityRender.render(context, graphics, tileInfo, city);
        }
    }
}
