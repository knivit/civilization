package com.tsoft.civilization.web.render.tile;

import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
public class ImageRender {

    private final String imageFileName;

    // lazy load
    private Image image;
    private final Observer observer = new Observer();

    public void render(Graphics g, int x, int y) {
        loadImage();
        g.drawImage(image, x, y, observer);
    }

    private static class Observer implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return true;
        }
    }

    private synchronized void loadImage() {
        URL resource = getClass().getResource(imageFileName);

        try {
            ImageIO.read(resource);
        } catch (IOException e) {
            throw new IllegalStateException("Can't load image " + resource.getFile());
        }
    }
}
