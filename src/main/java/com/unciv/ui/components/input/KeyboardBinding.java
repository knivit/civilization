package com.unciv.ui.components.input;

import com.badlogic.gdx.Input;
import com.unciv.Constants;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.unciv.ui.components.input.KeyboardBindingCategory.*;

@Getter
public enum KeyboardBinding {

    /** Used by [KeyShortcutDispatcher.KeyShortcut] to mark an old-style shortcut with a hardcoded key */
    None(KeyboardBindingCategory.None, KeyCharAndCode.UNKNOWN),

    // MainMenu
    Resume(MainMenu, (KeyCharAndCode) null),
    Quickstart(MainMenu, (KeyCharAndCode) null),
    StartNewGame(MainMenu, "Start new game", new KeyCharAndCode('N')),  // Not to be confused with NewGame (from World menu, Ctrl-N)
    MainMenuLoad(MainMenu, "Load game", new KeyCharAndCode('L')),
    Multiplayer(MainMenu, (KeyCharAndCode) null),  // Name disambiguation maybe soon, not yet necessary
    MapEditor(MainMenu, "Map editor", new KeyCharAndCode('E')),
    ModManager(MainMenu, "Mods", new KeyCharAndCode('D')),
    MainMenuOptions(MainMenu, "Options", new KeyCharAndCode('O')),  // Separate binding from World where it's Ctrl-O default

    // Worldscreen
    Menu(WorldScreen, KeyCharAndCode.TAB),
    NextTurn(WorldScreen, (KeyCharAndCode) null),
    NextTurnAlternate(WorldScreen, KeyCharAndCode.SPACE),
    EmpireOverview(WorldScreen, (KeyCharAndCode) null),
    MusicPlayer(WorldScreen, KeyCharAndCode.ctrl('m')),

    /*
     * These try to be faithful to default Civ5 key bindings as found in several places online
     * Some are a little arbitrary, e.g. Economic info, Military info
     * Some are very much so as Unciv *is* Strategic View.
     * The comments show a description like found in the mentioned sources for comparison.
     * @see http://gaming.stackexchange.com/questions/8122/ddg#8125
     */
    Civilopedia(WorldScreen, Input.Keys.F1),                 // Civilopedia
    EmpireOverviewTrades(WorldScreen, Input.Keys.F2),        // Economic info
    EmpireOverviewUnits(WorldScreen, Input.Keys.F3),         // Military info
    EmpireOverviewPolitics(WorldScreen, Input.Keys.F4),      // Diplomacy info
    SocialPolicies(WorldScreen, Input.Keys.F5),              // Social Policies Screen
    TechnologyTree(WorldScreen, Input.Keys.F6),              // Tech Screen
    EmpireOverviewNotifications(WorldScreen, Input.Keys.F7), // Notification Log
    VictoryScreen(WorldScreen, "Victory status", Input.Keys.F8),    // Victory Progress
    EmpireOverviewStats(WorldScreen, Input.Keys.F9),         // Demographics
    EmpireOverviewResources(WorldScreen, Input.Keys.F10),    // originally Strategic View
    QuickSave(WorldScreen, Input.Keys.F11),                  // Quick Save
    QuickLoad(WorldScreen, Input.Keys.F12),                  // Quick Load
    ViewCapitalCity(WorldScreen, Input.Keys.HOME),           // Capital City View
    Options(WorldScreen, KeyCharAndCode.ctrl('o')),    // Game Options
    SaveGame(WorldScreen, KeyCharAndCode.ctrl('s')),   // Save
    LoadGame(WorldScreen, KeyCharAndCode.ctrl('l')),   // Load
    ToggleResourceDisplay(WorldScreen, KeyCharAndCode.ctrl('r')),  // Show Resources Icons
    ToggleYieldDisplay(WorldScreen, KeyCharAndCode.ctrl('y')),  // Yield Icons, originally just "Y"
    // End of Civ5-inspired bindings

    QuitGame(WorldScreen, KeyCharAndCode.ctrl('q')),
    NewGame(WorldScreen, KeyCharAndCode.ctrl('n')),
    Diplomacy(WorldScreen, KeyCharAndCode.UNKNOWN),
    Espionage(WorldScreen, KeyCharAndCode.UNKNOWN),
    Undo(WorldScreen, KeyCharAndCode.ctrl('z')),
    ToggleUI(WorldScreen, "Toggle UI", KeyCharAndCode.ctrl('u')),
    ToggleWorkedTilesDisplay(WorldScreen, KeyCharAndCode.UNKNOWN),
    ToggleMovementDisplay(WorldScreen, KeyCharAndCode.UNKNOWN),
    ZoomIn(WorldScreen, Input.Keys.NUMPAD_ADD),
    ZoomOut(WorldScreen, Input.Keys.NUMPAD_SUBTRACT),

    // Map Panning - separate to get own expander. Map editor use will need to check this - it's arrows only
    PanUp(MapPanning, Input.Keys.UP),
    PanLeft(MapPanning, Input.Keys.LEFT),
    PanDown(MapPanning, Input.Keys.DOWN),
    PanRight(MapPanning, Input.Keys.RIGHT),
    PanUpAlternate(MapPanning, 'W'),
    PanLeftAlternate(MapPanning, 'A'),
    PanDownAlternate(MapPanning, 'S'),
    PanRightAlternate(MapPanning, 'D'),

    // Unit actions - name MUST correspond to UnitActionType.name because the shorthand constructor
    // there looks up bindings here by name - which also means we must not use UnitActionType
    // here as it will not be guaranteed to already be fully initialized.
    SwapUnits(UnitActions,"Swap units", 'y'),
    Automate(UnitActions, 'm'),
    StopAutomation(UnitActions,"Stop automation", 'm'),
    StopMovement(UnitActions,"Stop movement", '.'),
    ShowUnitDestination(UnitActions, "Show unit destination", 'j'),
    Sleep(UnitActions, 'f'),
    SleepUntilHealed(UnitActions,"Sleep until healed", 'h'),
    Fortify(UnitActions, 'f'),
    FortifyUntilHealed(UnitActions,"Fortify until healed", 'h'),
    Explore(UnitActions, 'x'),
    StopExploration(UnitActions,"Stop exploration", 'x'),
    Promote(UnitActions, 'o'),
    Upgrade(UnitActions, 'u'),
    Transform(UnitActions, 'k'),
    Pillage(UnitActions, 'p'),
    Paradrop(UnitActions, 'p'),
    AirSweep(UnitActions, 'a'),
    SetUp(UnitActions,"Set up", 't'),
    FoundCity(UnitActions,"Found city", 'c'),
    ConstructImprovement(UnitActions,"Construct improvement", 'i'),
    Repair(UnitActions, Constants.repair, 'r'),
    Create(UnitActions, 'i'),
    HurryResearch(UnitActions, 'g'),
    StartGoldenAge(UnitActions, 'g'),
    HurryWonder(UnitActions, 'g'),
    HurryBuilding(UnitActions,"Hurry Construction", 'g'),
    ConductTradeMission(UnitActions, 'g'),
    FoundReligion(UnitActions,"Found a Religion", 'g'),
    TriggerUnique(UnitActions,"Trigger unique", 'g'),
    SpreadReligion(UnitActions, 'g'),
    RemoveHeresy(UnitActions, 'h'),
    EnhanceReligion(UnitActions,"Enhance a Religion", 'g'),
    DisbandUnit(UnitActions,"Disband unit", KeyCharAndCode.DEL),
    GiftUnit(UnitActions,"Gift unit", KeyCharAndCode.UNKNOWN),
    Wait(UnitActions, 'z'),
    ShowAdditionalActions(UnitActions,"Show more", Input.Keys.PAGE_DOWN),
    HideAdditionalActions(UnitActions,"Back", Input.Keys.PAGE_UP),
    AddInCapital(UnitActions, "Add in capital", 'g'),

    // City Screen
    AddConstruction(CityScreen, "Add to or remove from queue", KeyCharAndCode.RETURN),
    RaisePriority(CityScreen, "Raise queue priority", Input.Keys.UP),
    LowerPriority(CityScreen, "Lower queue priority", Input.Keys.DOWN),
    BuyConstruction(CityScreen, 'b'),
    BuyTile(CityScreen, 't'),
    BuildUnits(CityScreen, "Buildable Units", 'u'),
    BuildBuildings(CityScreen, "Buildable Buildings", 'l'),
    BuildWonders(CityScreen, "Buildable Wonders", 'w'),
    BuildNationalWonders(CityScreen, "Buildable National Wonders", 'n'),
    BuildOther(CityScreen, "Other Constructions", 'o'),
    NextCity(CityScreen, Input.Keys.RIGHT),
    PreviousCity(CityScreen, Input.Keys.LEFT),
    ShowStats(CityScreen, 's'),
    ShowStatDetails(CityScreen, "Toggle Stat Details", Input.Keys.NUMPAD_ADD),
    CitizenManagement(CityScreen, 'c'),
    GreatPeopleDetail(CityScreen, 'g'),
    SpecialistDetail(CityScreen, 'p'),
    ReligionDetail(CityScreen, 'r'),
    BuildingsDetail(CityScreen, 'd'),
    ResetCitizens(CityScreen, KeyCharAndCode.ctrl('r')),
    AvoidGrowth(CityScreen, KeyCharAndCode.ctrl('a')),
    // The following are automatically matched by enum name to CityFocus entries - if necessary override there
    // Note on label: copied from CityFocus to ensure same translatable is used - without we'd get "Food Focus", not the same as "[Food] Focus"
    NoFocus(CityScreen, "Default Focus", KeyCharAndCode.ctrl('d')),
    FoodFocus(CityScreen, "[${Stat.Food.name}] Focus", KeyCharAndCode.ctrl('f')),
    ProductionFocus(CityScreen, "[${Stat.Production.name}] Focus", KeyCharAndCode.ctrl('p')),
    GoldFocus(CityScreen, "[${Stat.Gold.name}] Focus", KeyCharAndCode.ctrl('g')),
    ScienceFocus(CityScreen, "[${Stat.Science.name}] Focus", KeyCharAndCode.ctrl('s')),
    CultureFocus(CityScreen, "[${Stat.Culture.name}] Focus", KeyCharAndCode.ctrl('c')),
    FaithFocus(CityScreen, "[${Stat.Faith.name}] Focus", KeyCharAndCode.UNKNOWN),

    // CityScreenConstructionMenu (not quite cleanly) reuses RaisePriority/LowerPriority, plus:
    AddConstructionTop(CityScreenConstructionMenu, "Add to the top of the queue", 't'),
    AddConstructionAll(CityScreenConstructionMenu, "Add to the queue in all cities", KeyCharAndCode.ctrl('a')),
    AddConstructionAllTop(CityScreenConstructionMenu, "Add or move to the top in all cities", KeyCharAndCode.ctrl('t')),
    RemoveConstructionAll(CityScreenConstructionMenu, "Remove from the queue in all cities", KeyCharAndCode.ctrl('r')),

    // Popups
    Confirm(Popups, "Confirm Dialog", 'y'),
    Cancel(Popups, "Cancel Dialog", 'n'),
    UpgradeAll(Popups, KeyCharAndCode.ctrl('a'));

    private final KeyboardBindingCategory category;
    private final String label;
    private final KeyCharAndCode defaultKey;

    private static final Map<String, KeyboardBinding> MAP_BY_NAME = Arrays.stream(KeyboardBinding.values())
        .collect(Collectors.toMap(Enum::name, v -> v));

    KeyboardBinding(KeyboardBindingCategory category, String label, KeyCharAndCode key) {
        this.category = category;
        this.label = (label == null) ? unCamelCase(name()) : null;
        this.defaultKey = (key == null) ? new KeyCharAndCode(name().charAt(0)) : key;
    }

    KeyboardBinding(KeyboardBindingCategory category, String label, Character key) {
        this(category, label, new KeyCharAndCode(key));
    }

    KeyboardBinding(KeyboardBindingCategory category, String label, Integer key) {
        this(category, label, new KeyCharAndCode(key));
    }

    KeyboardBinding(KeyboardBindingCategory category, KeyCharAndCode key) {
        this(category, null, key);
    }

    KeyboardBinding(KeyboardBindingCategory category, Character key) {
        this(category, new KeyCharAndCode(key));
    }

    KeyboardBinding(KeyboardBindingCategory category, Integer key) {
        this(category, new KeyCharAndCode(key));
    }

    public static KeyboardBinding findByName(String name) {
        return MAP_BY_NAME.get(name);
    }

    private static final Pattern unCamelCaseRegex = Pattern.compile("([A-Z])([A-Z])([a-z])|([a-z])([A-Z])");

    private String unCamelCase(String text) {
        return unCamelCaseRegex.matcher(text).replaceAll("\\$1\\$4 \\$2\\$3\\$5");
    }
}
