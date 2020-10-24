package com.tsoft.civilization.web.render;

import java.awt.*;

public interface Render<T> {

    void render(RenderContext context, Graphics g, int x, int y, T tile);

    default int h(String hex) {
        return Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
    }
}
