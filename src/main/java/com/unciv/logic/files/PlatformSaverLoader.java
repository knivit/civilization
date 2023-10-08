package com.unciv.logic.files;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Contract for platform-specific helper classes to handle saving and loading games to and from
 * arbitrary external locations.
 *
 * Implementation note: If a game is loaded with [loadGame] and the same game is saved with [saveGame],
 * the suggestedLocation in [saveGame] will be the location returned by [loadGame].
 */
public interface PlatformSaverLoader {

    void saveGame(
        String data,                            // Data to save
        String suggestedLocation,               // Proposed location
        Consumer<String> onSaved,               // On-save-complete callback
        Consumer<Exception> onError             // On-save-error callback
    );

    void loadGame(
        BiConsumer<String, String> onLoaded,    // On-load-complete callback
        Consumer<Exception> onError             // On-load-error callback
    );

    class None implements PlatformSaverLoader {

        @Override
        public void saveGame(String data, String suggestedLocation, Consumer<String> onSaved, Consumer<Exception> onError) {

        }

        @Override
        public void loadGame(BiConsumer<String, String> onLoaded, Consumer<Exception> onError) {

        }
    }
}
