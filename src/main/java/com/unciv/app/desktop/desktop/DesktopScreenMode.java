package com.unciv.app.desktop.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.unciv.models.metadata.GameSettings;
import com.unciv.app.desktop.display.ScreenMode;

import java.awt.*;

import static com.tsoft.civilization.util.Kotlin.coerceIn;

public enum DesktopScreenMode implements ScreenMode {

    // Order will be preserved in Options Popup
    Windowed {
        @Override
        public void activate(GameSettings settings) {
            Gdx.graphics.setUndecorated(false);
            boolean isFillingDesktop = setWindowedMode(settings);
            if (isFillingDesktop) {
                getWindow().maximizeWindow();
            }
        }

        @Override
        public boolean hasUserSelectableSize() {
            return true;
        }
    },

    Fullscreen {
        @Override
        public void activate(GameSettings settings) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        @Override
        public boolean hasUserSelectableSize() {
            return false;
        }
    },

    Borderless {
        @Override
        public void activate(GameSettings settings) {
            getWindow().restoreWindow();
            Gdx.graphics.setUndecorated(true);
            setWindowedMode(settings);
        }

        @Override
        public boolean hasUserSelectableSize() {
            return true;
        }
    };

    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String toString() {
        return this.name();
    }

    public abstract void activate(GameSettings settings);

    /** @return `true` if window fills entire desktop */
    protected boolean setWindowedMode(GameSettings settings) {
        // Calling AWT after Gdx is fully initialized seems icky, but seems to have no side effects
        // Found no equivalent in Gdx - available _desktop_ surface without taskbars etc
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();

        // Make sure an inappropriate saved size doesn't make the window unusable
        int width = coerceIn(settings.getWindowState().getWidth(), 120, maximumWindowBounds.width);
        int height = coerceIn(settings.getWindowState().getHeight(), 80, maximumWindowBounds.height);

        // Kludge - see also DesktopLauncher - without, moving the window might revert to the size stored in config
        //(Lwjgl3Application.class).getDeclaredField("config").run {
        //    isAccessible = true
        //    get(Gdx.app) as Lwjgl3ApplicationConfiguration
        //}.setWindowedMode(width, height)

        Gdx.graphics.setWindowedMode(width, height);

        return width == maximumWindowBounds.width && height == maximumWindowBounds.height;
    }

    public static DesktopScreenMode get(int id) {
        return values()[id];
    }

    private static Lwjgl3Window getWindow() {
        return ((Lwjgl3Graphics)(Gdx.graphics)).getWindow();
    }
}
