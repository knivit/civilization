package com.unciv.logic.files;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.tsoft.civilization.Civ5;
import com.tsoft.civilization.game.json.JsonFactory;
import com.unciv.models.metadata.GameSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GameFiles {

    private static final String SETTINGS_FILE_NAME = "GameSettings.json";

    private final Files files;

    private FileHandle getGeneralSettingsFile() {
        return (Civ5.Current.isConsoleMode) ?
                new FileHandle(SETTINGS_FILE_NAME) : files.local(SETTINGS_FILE_NAME);
    }

    public GameSettings getGeneralSettings() {
        FileHandle settingsFile = getGeneralSettingsFile();
        GameSettings settings = null;
        if (settingsFile.exists()) {
            try {
                settings = JsonFactory.json().fromJson(GameSettings.class, settingsFile);
            } catch (Exception ex) {
                // I'm not sure of the circumstances,
                // but some people were getting null settings, even though the file existed??? Very odd.
                // ...Json broken or otherwise unreadable is the only possible reason.
                log.error("Error reading settings file", ex);
            }
        }

        if (settings == null) {
            settings = new GameSettings().setFreshlyCreated(true);
        }

        return settings;
    }

}
