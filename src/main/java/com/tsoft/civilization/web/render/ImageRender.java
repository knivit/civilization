package com.tsoft.civilization.web.render;

import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

@RequiredArgsConstructor
public class ImageRender {

    private final String imageFileName;

    // lazy load
    private Image image;
    private final Observer observer = new Observer();

    public void render(GraphicsContext graphicsContext, int x, int y) {
        loadImage();
        graphicsContext.getG().drawImage(image, x, y, observer);
    }

    private static class Observer implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return true;
        }
    }

    private synchronized void loadImage() {
        if (image != null) {
            return;
        }

        URL resource = getClass().getClassLoader().getResource(imageFileName);
        try {
            image = ImageIO.read(resource);
        } catch (Exception e) {
            throw new IllegalStateException("Can't load image from " + imageFileName);
        }
    }
}
