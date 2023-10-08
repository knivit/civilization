package com.unciv;

import com.tsoft.civilization.Civ5;
import com.unciv.models.metadata.GameSettings;
import com.unciv.ui.screens.basescreen.BaseScreen;

public class GUI {

    public static void setUpdateWorldOnNextRender() {
        Civ5.Current.worldScreen.shouldUpdate = true;
    }

    public static void pushScreen(BaseScreen screen) {
        Civ5.Current.pushScreen(screen);
    }

/*    public static void resetToWorldScreen() {
        Civ5.Current.resetToWorldScreen();
    }
*/
    public static GameSettings getSettings() {
        return Civ5.Current.settings;
    }

    public static boolean isWorldLoaded() {
        return Civ5.Current.worldScreen != null;
    }

/*    public static boolean isMyTurn() {
        if (!Civ5.isCurrentInitialized() || !isWorldLoaded()) return false;
        return Civ5.Current.worldScreen!!.isPlayersTurn;
    }

    public static boolean isAllowedChangeState() {
        return Civ5.Current.worldScreen!!.canChangeState;
    }

    public static WorldScreen getWorldScreen() {
        return Civ5.Current.worldScreen;
    }

    public static WorldScreen getWorldScreenIfActive() {
        return Civ5.Current.getWorldScreenIfActive();
    }

    public static WorldMapHolder getMap() {
        return Civ5.Current.worldScreen.mapHolder;
    }

    public static UnitTable getUnitTable() {
        return Civ5.Current.worldScreen.bottomUnitTable;
    }

    public static Civilization getViewingPlayer() {
        return Civ5.Current.worldScreen!!.viewingCiv;
    }

    public static Civilization getSelectedPlayer() {
        return Civ5.Current.worldScreen!!.selectedCiv;
    }

    private Boolean keyboardAvailableCache;

    // Tests availability of a physical keyboard
    val keyboardAvailable: Boolean
    get() {
        // defer decision if Gdx.input not yet initialized
        if (keyboardAvailableCache == null && Gdx.input != null)
            keyboardAvailableCache = Gdx.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard)
        return keyboardAvailableCache ?: false
    }
*/
}
