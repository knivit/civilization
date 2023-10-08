package com.unciv.app.desktop;

import com.unciv.logic.files.PlatformSaverLoader;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DesktopSaverLoader implements PlatformSaverLoader {

    @Override
    public void saveGame(String data, String suggestedLocation, Consumer<String> onSaved, Consumer<Exception> onError) {

    }

    @Override
    public void loadGame(BiConsumer<String, String> onLoaded, Consumer<Exception> onError) {

    }
}
