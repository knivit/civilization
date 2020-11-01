package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.unit.UnitRenderCatalog;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class WorldRender {

    private final Class<?> clazz;
    private int n;

    private final RenderCatalog<AbstractUnit> unitRenderCatalog = new UnitRenderCatalog();

    public WorldRender(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void createPng(World world) {
        TilesMap map = world.getTilesMap();
        RenderContext renderContext = new RenderContext(map.getWidth(), map.getHeight(), 120, 120);
        GraphicsContext graphicsContext = new GraphicsContext((int)renderContext.getMapWidthX(), (int)renderContext.getMapHeightY()).build();

        MapRender mapRender = new MapRender(clazz);
        mapRender.drawTiles(renderContext, graphicsContext, map);
        drawUnits(renderContext, graphicsContext, world.getCivilizations());
        //drawCities(drawArea);
        drawCivilizationsBoundaries(renderContext, graphicsContext, world.getCivilizations());

        String path = "target/" + clazz.getPackageName().replace('.', '/') + "/" + clazz.getSimpleName();
        String outputFileName = path + "/" + (++ n) + "_world_" + map.getWidth() + "x" + map.getHeight() + ".png";
        graphicsContext.saveImageToFile(outputFileName);
    }

    private void drawCivilizationsBoundaries(RenderContext context, GraphicsContext graphicsContext, CivilizationList civilizations) {
        civilizations.forEach(c -> drawCivilizationBoundary(context, graphicsContext, c));
    }

    private void drawCivilizationBoundary(RenderContext context, GraphicsContext graphicsContext, Civilization civilization) {
        Collection<Point> points = civilization.territory().getCivilizationLocations();
    }

    private void drawUnits(RenderContext context, GraphicsContext graphics, CivilizationList civilizations) {
        civilizations.stream()
            .flatMap(c -> c.units().stream())
            .forEach(u -> drawUnit(context, graphics, context.getTileInfo(u.getLocation()), u));
    }

    private void drawUnit(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, AbstractUnit unit) {
        if (tileInfo != null) {
            unitRenderCatalog.render(context, graphics, tileInfo, unit);
        }
    }
}
