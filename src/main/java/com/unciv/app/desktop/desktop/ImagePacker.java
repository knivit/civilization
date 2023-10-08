package com.unciv.app.desktop.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.tsoft.civilization.game.json.JsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Entry point: _ImagePacker.[packImages] ()_
 *
 * Re-packs our texture assets into atlas + png File pairs, which will be loaded by the game.
 * With the exception of the ExtraImages folder and the Font system these are the only
 * graphics used (The source Image folders are unused at run time except here).
 *
 * [TexturePacker] documentation is [here](https://github.com/libgdx/libgdx/wiki/Texture-packer)
 */
@Slf4j
public class ImagePacker {

    private static TexturePacker.Settings getDefaultSettings() {
        // Apparently some chipsets, like NVIDIA Tegra 3 graphics chipset (used in Asus TF700T tablet),
        // don't support non-power-of-two texture sizes - kudos @yuroller!
        // https://github.com/yairm210/Unciv/issues/1340

        /**
         * These should be as big as possible in order to accommodate ALL the images together in one big file.
         * Why? Because the rendering function of the main screen renders all the images consecutively,
         * and every time it needs to switch between textures,
         * this causes a delay, leading to horrible lag if there are enough switches.
         * The cost of this specific solution is that the entire game.png needs to be kept in-memory constantly.
         * Now here we come to what Fred Colon would call an Imp Arse.
         * On the one hand, certain tilesets (ahem 5hex ahem) are really big.
         * You wouldn't believe how hugely mindbogglingly big they are. So theoretically we should want all of their images to be together.
         * HOWEVER certain chipsets (see https://github.com/yairm210/Unciv/issues/3330) only seem to support to up to 2048 width*height so this is maximum we can have.
         * Practically this means that big custom tilesets will have to reload the texture a lot when covering the map and so the
         *    panning on the map will tend to lag a lot :(
         *
         *    TL;DR this should be 2048.
         */
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;

        // Trying to disable the subdirectory combine lead to even worse results. Don't.
        settings.combineSubdirectories = true;

        settings.pot = true;  // powers of two only for width/height
        settings.fast = true;  // with pot on this just sorts by width
        // settings.rotation - do not set. Allows rotation, potentially packing tighter.
        //      Proper rendering is mostly automatic - except borders which overwrite rotation.

        // Set some additional padding and enable duplicatePadding to prevent image edges from bleeding into each other due to mipmapping
        settings.paddingX = 8;
        settings.paddingY = 8;
        settings.duplicatePadding = true;
        settings.filterMin = Texture.TextureFilter.MipMapLinearLinear;
        settings.filterMag = Texture.TextureFilter.MipMapLinearLinear; // I'm pretty sure this doesn't make sense for magnification, but setting it to Linear gives strange results

        return settings;
    }

    public static void packImages(boolean isRunFromJAR) {
        long startTime = System.currentTimeMillis();

        TexturePacker.Settings defaultSettings = getDefaultSettings();

        // Scan for Image folders and build one atlas each
        if (!isRunFromJAR) {
            packImagesPerMod("..", ".", defaultSettings);
        }

        // pack for mods
        File modDirectory = new File("mods");
        if (modDirectory.exists()) {
            for (File mod : modDirectory.listFiles()) {
                if (!mod.isHidden()) {
                    try {
                        packImagesPerMod(mod.getPath(), mod.getPath(), defaultSettings);
                    } catch (Exception ex) {
                        log.error("Exception in ImagePacker: {}", ex.getMessage());
                    }
                }
            }
        }

        long texturePackingTime = System.currentTimeMillis() - startTime;
        log.debug("Packing textures - {} ms", texturePackingTime);
    }

    // Scan multiple image folders and generate an atlas for each - if outdated
    private static void packImagesPerMod(String input, String output, TexturePacker.Settings defaultSettings) {
        if (! new File(input + "/Images").exists()) return;  // So we don't run this from within a fat JAR

        List<String> atlasList = new ArrayList<>();
        for (ImageFolderResult res : imageFolders(input)) {
            atlasList.add(res.atlasName);
            packImagesIfOutdated(defaultSettings, res.folder, output, res.atlasName);
        }

        atlasList.remove("game");
        File listFile = new File(output + "/Atlases.json");
        if (atlasList.isEmpty()) listFile.delete();
        else {
            String text = "[" + atlasList.stream().sorted().collect(Collectors.joining(",")) + "]";
            try {
                Files.writeString(Path.of(listFile.toURI()), text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static List<File> listTree(File file) {
        try (Stream<Path> stream = Files.find(Paths.get(file.toURI()), 8, (p, bfa) -> bfa.isRegularFile())) {
            return stream.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Process one Image folder, checking for atlas older than contained images first
    private static void packImagesIfOutdated(TexturePacker.Settings defaultSettings, String input, String output, String packFileName) {
        // Check if outdated
        File atlasFile = new File(output + "/" + packFileName + ".atlas");
        if (atlasFile.exists() && new File(output + "/" + packFileName + ".png").exists()) {
            long atlasModTime = atlasFile.lastModified();
            //if (new File(input).listTree().none {
            //    val attr: BasicFileAttributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
            //    val createdAt: Long = attr.creationTime().toMillis()
            //    it.extension in listOf("png", "jpg", "jpeg")
            //            && (it.lastModified() > atlasModTime || createdAt > atlasModTime)
            //}) return
        }

        // An image folder can optionally have a TexturePacker settings file
        File settingsFile = new File(input + "/TexturePacker.settings");
        TexturePacker.Settings settings;
        try {
            settings = (settingsFile.exists()) ?
                JsonFactory.json().fromJson(TexturePacker.Settings.class, new FileReader(settingsFile, StandardCharsets.UTF_8)) :
                defaultSettings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TexturePacker.process(settings, input, output, packFileName);
    }

    // Iterator providing all Image folders to process with the destination atlas name
    @RequiredArgsConstructor
    private static class ImageFolderResult {
        final String folder;
        final String atlasName;
    }

    private static List<ImageFolderResult> imageFolders(String path) {
        List<ImageFolderResult> results = new ArrayList<>();

        File parent = new File(path);
        for (File folder : parent.listFiles()) {
            if (!folder.isDirectory()) continue;
            if (!"Images".equals(getNameWithoutExtension(folder))) continue;
            String atlasName = ("Images".equals(folder.getName())) ? "game" : getExtension(folder);

            results.add(new ImageFolderResult(folder.getPath(), atlasName));
        }
        return results;
    }

    private static String getNameWithoutExtension(File file) {
        String name = file.getName();
        int n = name.indexOf('.');
        return (n == -1) ? name : name.substring(0, n);
    }

    private static String getExtension(File file) {
        String name = file.getName();
        int n = name.indexOf('.');
        return (n == -1) ? null : name.substring(n);
    }
}
