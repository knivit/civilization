package com.tsoft.civilization.web.render;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class RenderCatalog<T> {

    private final Map<Class<T>, Render<T>> renders;

    public void render(RenderContext renderContext, GraphicsContext graphicsContext, RenderTileInfo tileInfo, T objToRender) {
        Render<T> render = renders.get(objToRender.getClass());
        if (render == null) {
            throw new IllegalArgumentException("No render for " + objToRender.getClass().getName());
        }

        render.render(renderContext, graphicsContext, tileInfo, objToRender);
    }
}
