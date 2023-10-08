package com.unciv.models.metadata;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.tsoft.civilization.Civ5;
import com.unciv.ui.components.FontFamilyData;
import com.unciv.ui.components.Fonts;
import com.unciv.utils.Display;
import com.unciv.utils.ScreenOrientation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.text.Collator;
import java.util.*;

@Data
@Accessors(chain = true)
public class GameSettings {

    /** Allows panning the map by moving the pointer to the screen edges */
    public boolean mapAutoScroll;

    /** How fast the map pans using keyboard or with [mapAutoScroll] and mouse */
    public float mapPanningSpeed = 6f;

    public boolean showWorkedTiles;
    public boolean showResourcesAndImprovements = true;
    public boolean showTileYields;
    public boolean showUnitMovements;
    public boolean showSettlersSuggestedCityLocations = true;

    public boolean checkForDueUnits = true;
    public boolean autoUnitCycle = true;
    public boolean singleTapMove;
    public String language = Constants.english;

    public Locale locale;
    public ScreenSize screenSize = ScreenSize.Small;
    public int screenMode;
    public Set<String> tutorialsShown = new HashSet<>();
    public Set<String> tutorialTasksCompleted = new HashSet<>();

    public float soundEffectsVolume = 0.5f;
    public float citySoundsVolume = 0.5f;
    public float musicVolume = 0.5f;
    public int pauseBetweenTracks = 10;

    public int turnsBetweenAutosaves = 1;
    public String tileSet = Constants.defaultTileset;
    public String unitSet = Constants.defaultUnitset;
    public String skin = Constants.defaultSkin;
    public boolean showTutorials = true;
    public boolean autoAssignCityProduction = true;
    public boolean autoBuildingRoads = true;
    public boolean automatedWorkersReplaceImprovements = true;
    public boolean automatedUnitsMoveOnTurnStart;
    public boolean automatedUnitsCanUpgrade;
    public boolean automatedUnitsChoosePromotions;

    public boolean showMinimap = true;
    public int minimapSize = 6;    // default corresponds to 15% screen space
    public float unitIconOpacity = 1f; // default corresponds to fully opaque
    public boolean showPixelUnits = true;
    public boolean showPixelImprovements = true;
    public boolean continuousRendering;
    public boolean orderTradeOffersByAmount = true;
    public boolean confirmNextTurn;
    public WindowState windowState = new WindowState();
    public boolean isFreshlyCreated;
    public Set<String> visualMods = new HashSet<>();
    public boolean useDemographics;
    public boolean showZoomButtons;

    public int notificationsLogMaxTurns = 5;

    public boolean showAutosaves;

    public boolean androidCutout;

    public GameSettingsMultiplayer multiplayer = new GameSettingsMultiplayer();

    boolean enableEspionageOption;

    // This is a string not an enum so if tabs change it won't screw up the json serialization
    var lastOverviewPage = EmpireOverviewCategories.Cities.name;

    /** Orientation for mobile platforms */
    ScreenOrientation displayOrientation = ScreenOrientation.Landscape;

    /** Saves the last successful new game's setup */
    GameSetupInfo lastGameSetup;

    public FontFamilyData fontFamilyData = FontFamilyData.DEFAULT;
    public float fontSizeMultiplier = 1f;

    public boolean enableEasterEggs = true;

    /** Maximum zoom-out of the map - performance heavy */
    float maxWorldZoomOut = 2f;

    public KeyboardBindings keyBindings = new KeyboardBindings();

    /** NotificationScroll on Word Screen visibility control - mapped to NotificationsScroll.UserSetting enum */
    public String notificationScroll = "";

    /** If on, selected notifications are drawn enlarged with wider padding */
    public boolean enlargeSelectedNotification = true;

    /** Whether the Nation Picker shows icons only or the horizontal "civBlocks" with leader/nation name */
    public NationPickerListMode nationPickerListMode = NationPickerListMode.List;

    /** used to migrate from older versions of the settings */
    Integer version;

    public GameSettings() {
        // 26 = Android Oreo. Versions below may display permanent icon in notification bar.
        if (Gdx.app.getType() == Application.ApplicationType.Android && Gdx.app.getVersion() < 26) {
            multiplayer.turnCheckerPersistentNotificationEnabled = false;
        }
    }

    public void save() {
        refreshWindowSize();
        Civ5.Current.files.setGeneralSettings(this);
    }

    public void refreshWindowSize() {
        if (isFreshlyCreated || Gdx.app.getType() != Application.ApplicationType.Desktop) return;
        if (!Display.hasUserSelectableSize(screenMode)) return;
        windowState = new WindowState(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public boolean addCompletedTutorialTask(String tutorialTask) {
        if (!tutorialTasksCompleted.add(tutorialTask)) return false;
        Civ5.Current.isTutorialTaskCollapsed = false;
        save();
        return true;
    }

    public void updateLocaleFromLanguage() {
        List<Character> bannedCharacters = Arrays.asList(' ', '_', '-', '(', ')'); // Things not to have in enum names
        val languageName = language.filterNot { it in bannedCharacters }
        try {
            val code = LocaleCode.valueOf(languageName);
            locale = new Locale(code.language, code.country);
        } catch (Exception ex) {
            locale = Locale.getDefault();
        }
    }

    public int getFontSize() {
        return (int)(Fonts.ORIGINAL_FONT_SIZE * fontSizeMultiplier);
    }

    private Locale getCurrentLocale() {
        if (locale == null)
            updateLocaleFromLanguage();
        return locale;
    }

    public Collator getCollatorFromLocale() {
        return Collator.getInstance(getCurrentLocale());
    }
}
