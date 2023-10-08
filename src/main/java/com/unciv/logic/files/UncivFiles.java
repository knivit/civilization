package com.unciv.logic.files;

import com.badlogic.gdx.files.FileHandle;
import com.tsoft.civilization.game.json.JsonFactory;
import com.unciv.models.metadata.GameSettings;

public class UncivFiles {

    public static final String SETTINGS_FILE_NAME = "GameSettings.json";

    /**
     * If the GDX [com.badlogic.gdx.Files.getExternalStoragePath] should be preferred for this platform,
     * otherwise uses [com.badlogic.gdx.Files.getLocalStoragePath]
     */
    public static boolean preferExternalStorage;

    /**
     * Platform dependent saver-loader to custom system locations
     */
    public static PlatformSaverLoader saverLoader = new PlatformSaverLoader.None();
    //get() {
    //    if (field.javaClass.simpleName == "DesktopSaverLoader" && LinuxX11SaverLoader.isRequired())
    //        field = LinuxX11SaverLoader()
    //    return field
    //}

    public static GameSettings getSettingsForPlatformLaunchers() {
        return getSettingsForPlatformLaunchers(".");
    }

    /** Specialized function to access settings before Gdx is initialized.
     *
     * @param base Path to the directory where the file should be - if not set, the OS current directory is used (which is "/" on Android)
     */
    public static GameSettings getSettingsForPlatformLaunchers(String base) {
        // FileHandle is Gdx, but the class and JsonParser are not dependent on app initialization
        // In fact, at this point Gdx.app or Gdx.files are null but this still works.
        FileHandle file = new FileHandle(base + "/" + SETTINGS_FILE_NAME);
        if (file.exists()) {
            return JsonFactory.fromJsonFile(GameSettings.class, file);
        }

        GameSettings settings = new GameSettings();
        settings.setFreshlyCreated(true);
        return settings;
    }
}
