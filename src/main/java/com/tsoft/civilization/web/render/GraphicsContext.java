package com.tsoft.civilization.web.render;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@Slf4j
@Getter
public class GraphicsContext {

    // Rendered image size in pixels
    private final int widthX;
    private final int heightY;

    private BufferedImage img;
    private Graphics g;
    private Graphics2D g2;

    public GraphicsContext(int widthX, int heightY) {
        this.widthX = widthX;
        this.heightY = heightY;

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        log.debug("Available fonts: {}", (Object[]) fonts);
    }

    public GraphicsContext build() {
        img = new BufferedImage(widthX, heightY, TYPE_INT_RGB);
        g = img.getGraphics();
        g2 = (Graphics2D)g;
        return this;
    }

    public void saveImageToFile(Path outputFileName) {
        try {
            Files.deleteIfExists(outputFileName);
            Files.createDirectories(outputFileName);
        } catch (Exception e) {
            throw new IllegalStateException("Can't create file " + outputFileName, e);
        }

        try {
            ImageIO.write(img, "png", outputFileName.toFile());
            log.info("File {} generated", outputFileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
