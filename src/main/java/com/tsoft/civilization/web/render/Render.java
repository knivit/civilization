package com.tsoft.civilization.web.render;

public interface Render<T> {

    void render(RenderContext context, GraphicsContext graphicsContext, RenderTileInfo tileInfo, T objToRender);

    default int h(String hex) {
        return Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
    }

    static int r(float val) {
        return Math.round(val);
    }
}
