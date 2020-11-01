package com.tsoft.civilization.web.render;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@Getter
@Slf4j
public class GraphicsContext {

    // Rendered image size in pixels
    private final int widthX;
    private final int heightY;

    private BufferedImage img;
    private Graphics g;

    public GraphicsContext(int widthX, int heightY) {
        this.widthX = widthX;
        this.heightY = heightY;

        log.debug("Available fonts: {}", GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    }

    public GraphicsContext build() {
        img = new BufferedImage(widthX, heightY, TYPE_INT_RGB);
        g = img.getGraphics();
        return this;
    }

    public void saveImageToFile(String outputFileName) {
        try {
            ImageIO.write(img, "png", new File(outputFileName));
            log.info("File {} generated", outputFileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
