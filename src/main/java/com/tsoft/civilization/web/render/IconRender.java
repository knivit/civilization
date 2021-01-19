package com.tsoft.civilization.web.render;

import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

import static com.tsoft.civilization.web.render.Render.r;

@RequiredArgsConstructor
public class IconRender {

    private final String imageFileName;

    // lazy load
    private Image icon;
    private final Observer observer = new Observer();

    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo) {
        loadImage();

        int x = tileInfo.x;
        int y = tileInfo.y;
        float[] ox = context.getHexBordersX();
        float[] oy = context.getHexBordersY();
        graphics.getG().drawImage(icon, r(x), r(y + oy[0]), r(ox[3] - ox[0]), r(oy[3] - oy[2]), observer);
    }

    private static class Observer implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return true;
        }
    }

    private synchronized void loadImage() {
        if (icon != null) {
            return;
        }

        URL resource = getClass().getClassLoader().getResource(imageFileName);
        try {
            icon = ImageIO.read(resource);
        } catch (Exception e) {
            throw new IllegalStateException("Can't load image from " + imageFileName);
        }
    }
}
