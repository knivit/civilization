package com.unciv.app.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.unciv.app.desktop.desktop.ImagePacker;
import com.unciv.ui.components.Fonts;
import com.tsoft.civilization.game.json.JsonFactory;
import com.unciv.logic.files.UncivFiles;
import com.unciv.models.metadata.GameSettings;
import com.unciv.models.metadata.ScreenSize;
import com.unciv.models.metadata.WindowState;
import com.unciv.ui.screens.basescreen.BaseScreen;
import com.unciv.utils.Display;
import com.unciv.app.desktop.desktop.DesktopDisplay;

import java.awt.*;
import java.nio.charset.StandardCharsets;

import static java.lang.Math.max;

public class DesktopLauncher {

    public static void main(String[] args) {
        // Setup Desktop display
        Display.platform = new DesktopDisplay();

        // Setup Desktop font
        Fonts.fontImplementation = new DesktopFont();

        // Setup Desktop saver-loader
        UncivFiles.saverLoader = new DesktopSaverLoader();
        UncivFiles.preferExternalStorage = false;

        // Solves a rendering problem in specific GPUs and drivers.
        // For more info see https://github.com/yairm210/Unciv/pull/3202 and https://github.com/LWJGL/lwjgl/issues/119
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        // This setting (default 64) limits clipboard transfers. Value in kB!
        // 386 is an almost-arbitrary choice from the saves I had at the moment and their GZipped size.
        // There must be a reason for lwjgl3 being so stingy, which for me meant to stay conservative.
        System.setProperty("org.lwjgl.system.stackSize", "384");

        boolean isRunFromJAR = DesktopLauncher.class.getPackage().getSpecificationVersion() != null;
        ImagePacker.packImages(isRunFromJAR);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowIcon("ExtraImages/Icon.png");
        config.setTitle("Civ5");
        config.setHdpiMode(HdpiMode.Logical);
        config.setWindowSizeLimits(120, 80, -1, -1);

        // We don't need the initial Audio created in Lwjgl3Application, HardenGdxAudio will replace it anyway.
        // Note that means config.setAudioConfig() would be ignored too, those would need to go into the HardenedGdxAudio constructor.
        config.disableAudio(true);

        GameSettings settings = UncivFiles.getSettingsForPlatformLaunchers();
        if (settings.isFreshlyCreated()) {
            settings.setScreenSize(ScreenSize.Large); // By default we guess that Desktops have larger screens
            // LibGDX not yet configured, use regular java class
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();
            settings.setWindowState(new WindowState(
                    maximumWindowBounds.width,
                    maximumWindowBounds.height
            ));
            new FileHandle(UncivFiles.SETTINGS_FILE_NAME)
                    .writeString(JsonFactory.json().toJson(settings), false, StandardCharsets.UTF_8.name()); // so when we later open the game we get fullscreen
        }
        // Kludge! This is a workaround - the matching call in DesktopDisplay doesn't "take" quite permanently,
        // the window might revert to the "config" values when the user moves the window - worse if they
        // minimize/restore. And the config default is 640x480 unless we set something here.
        config.setWindowedMode(max(settings.getWindowState().width, 100), max(settings.getWindowState().height, 100));
        config.setInitialBackgroundColor(BaseScreen.clearColor);

        if (!isRunFromJAR) {
            //UniqueDocsWriter().write();
            //UiElementDocsWriter().write();
        }

        DesktopGame game = new DesktopGame(config);
        new Lwjgl3Application(game, config);
    }
}
