package com.tsoft.civilization.web.render;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
public class WorldRender {

    private final RenderFileNameGenerator fileNameGenerator;

    public static WorldRender of(Object testClassObj) {
        return new WorldRender(testClassObj);
    }

    private WorldRender(Object testClassObj) {
        fileNameGenerator = new RenderFileNameGenerator(testClassObj.getClass(), "world");
    }

    public void createHtml(World world, Civilization activeCivilization) {
        Civilization currentCivilization = Sessions.getCurrent().getCivilization();

        try {
            Sessions.getCurrent().setActiveCivilization(activeCivilization);

            Path imageOutputFileName = fileNameGenerator.getOutputFileName(".png");
            Path htmlOutputFileName = fileNameGenerator.getOutputFileName(".html");

            ImageRender imageRender = new ImageRender();
            imageRender.createPng(world, imageOutputFileName);

            StatusContext statusContext = new StatusContext();
            statusContext.setImageFileName(imageOutputFileName);

            StatusRender statusRender = new StatusRender();
            statusRender.render(statusContext, world);

            statusContext.saveHtmlToFile(htmlOutputFileName);
        } finally {
            Sessions.getCurrent().setActiveCivilization(currentCivilization);
        }
    }
}
