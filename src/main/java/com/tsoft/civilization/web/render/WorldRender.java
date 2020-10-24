package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@Slf4j
public class WorldRender {

    private static final Map<Class<?>, Render<? extends AbstractUnit>> UNIT_RENDERS = new HashMap<>();
    static {
        UNIT_RENDERS.put(Archers.class, new ArchersRender());
        UNIT_RENDERS.put(Settlers.class, new SettlersRender());
        UNIT_RENDERS.put(Warriors.class, new WarriorsRender());
        UNIT_RENDERS.put(Workers.class, new WorkersRender());
    }

    private final Class<?> clazz;
    private int n;

    public WorldRender(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void createSvg(World world) {
        TilesMap map = world.getTilesMap();

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

        MapRender mapRender = new MapRender(clazz);
        mapRender.drawTiles(renderContext, renderArea, g, map);
        drawUnits(renderContext, g, world.getCivilizations());
        //drawCities(drawArea);
        drawCivilizationsBoundaries(renderContext, g, world.getCivilizations());

        saveImageTiFile(img, outputFileName);
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
