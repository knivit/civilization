package com.tsoft.civilization.web.render;

public interface Render<T> {

    void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, T objToRender);

    default int h(String hex) {
        return Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
    }
}
