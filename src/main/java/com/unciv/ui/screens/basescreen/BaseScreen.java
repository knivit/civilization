package com.unciv.ui.screens.basescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tsoft.civilization.Civ5;
import com.unciv.ui.images.ImageGetter;
import com.unciv.models.TutorialTrigger;
import com.unciv.models.skins.SkinStrings;
import com.unciv.ui.components.Fonts;
import com.unciv.models.metadata.ScreenSize;
import com.unciv.ui.components.extensions.Scene2dExtensions;
import com.unciv.ui.components.input.DispatcherVetoer;
import com.unciv.ui.components.input.KeyShortcutDispatcher;
import com.unciv.ui.crashhandling.CrashScreen;
import com.unciv.ui.popups.Popup;

import java.util.function.Supplier;

public abstract class BaseScreen implements Screen {

    public Civ5 game = Civ5.Current;
    public Stage stage;

    boolean enableSceneDebug = false;

    /** Colour to use for empty sections of the screen.
     *  Gets overwritten by SkinConfig.clearColor after starting Unciv */
    public static Color clearColor = new Color(0f, 0f, 0.2f, 1f);

    protected TutorialController tutorialController;

    /**
     * Keyboard shortcuts global to the screen. While this is public and can be modified,
     * you most likely should use [keyShortcuts] on the appropriate [Actor] instead.
     */
    public KeyShortcutDispatcher globalShortcuts = new KeyShortcutDispatcher();

    public BaseScreen() {
        ScreenSize screenSize = game.settings.getScreenSize();
        float height = screenSize.getVirtualHeight();

        /** The ExtendViewport sets the _minimum_(!) world size - the actual world size will be larger, fitted to screen/window aspect ratio. */
        stage = new UncivStage(new ExtendViewport(height, height));

        if (enableSceneDebug && !(this instanceof CrashScreen)) {
            stage.setDebugUnderMouse(true);
            stage.setDebugTableUnderMouse(true);
            stage.setDebugParentUnderMouse(true);
            //stage.mouseOverDebug = true;
        }

        //stage.installShortcutDispatcher(globalShortcuts, this::createDispatcherVetoer);
    }

    /** Hook allowing derived Screens to supply a key shortcut vetoer that can exclude parts of the
     *  Stage Actor hierarchy from the search. Only called if no [Popup] is active.
     *  @see installShortcutDispatcher
     */
    public DispatcherVetoer getShortcutDispatcherVetoer() {
        return null;
    }

    private DispatcherVetoer createDispatcherVetoer() {
        Popup activePopup = Popup.activePopup(this)
                ?: return getShortcutDispatcherVetoer();
        return KeyShortcutDispatcherVeto.createPopupBasedDispatcherVetoer(activePopup);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (this !is RecreateOnResize) {
            stage.viewport.update(width, height, true)
        } else if (stage.viewport.screenWidth != width || stage.viewport.screenHeight != height) {
            game.replaceCurrentScreen(recreate())
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    /**
     * Called when this screen should release all resources.
     *
     * This is _not_ called automatically by Gdx, but by the [screenStack][UncivGame.screenStack]
     * functions in [UncivGame], e.g. [replaceCurrentScreen][UncivGame.replaceCurrentScreen].
     */
    @Override
    public void dispose() {
        // FYI - This is a method of Gdx [Screen], not of Gdx [Disposable], but the one below _is_.
        stage.dispose();
    }

    public void displayTutorial(TutorialTrigger tutorial, Supplier<Boolean> test) {
        if (!game.settings.showTutorials) return;
        if (game.settings.tutorialsShown.contains(tutorial.name())) return;
        if (test != null && !test.get()) return;
        tutorialController.showTutorial(tutorial);
    }

    public static Skin skin;
    public static SkinStrings skinStrings;

    public static void setSkin() {
        Fonts.resetFont();
        skinStrings = new SkinStrings();
        skin = new Skin();
        skin.add("default-clear", clearColor, Color.class);
        skin.add("Nativefont", Fonts.font, BitmapFont.class);
        skin.add("RoundedEdgeRectangle", skinStrings.getUiBackground("", skinStrings.roundedEdgeRectangleShape, null), Drawable.class);
        skin.add("Rectangle", ImageGetter.getDrawable(""), Drawable.class);
        skin.add("Circle", ImageGetter.getDrawable("OtherIcons/Circle").apply { setMinSize(20f, 20f) }, Drawable.class);
        skin.add("Scrollbar", ImageGetter.getDrawable("").apply { setMinSize(10f, 10f) }, Drawable.class);
        skin.add("RectangleWithOutline", skinStrings.getUiBackground("", skinStrings.rectangleWithOutlineShape, null), Drawable.class);
        skin.add("Select-box", skinStrings.getUiBackground("", skinStrings.selectBoxShape, null), Drawable.class);
        skin.add("Select-box-pressed", skinStrings.getUiBackground("", skinStrings.selectBoxPressedShape, null), Drawable.class);
        skin.add("Checkbox", skinStrings.getUiBackground("", skinStrings.checkboxShape, null), Drawable.class);
        skin.add("Checkbox-pressed", skinStrings.getUiBackground("", skinStrings.checkboxPressedShape, null), Drawable.class);
        skin.load(Gdx.files.internal("Skin.json"));

        skin.get(TextButton.TextButtonStyle.class).font = Fonts.font;
        CheckBox.CheckBoxStyle cbs = skin.get(CheckBox.CheckBoxStyle.class);
        cbs.font = Fonts.font;
        cbs.fontColor = Color.WHITE;
        Label.LabelStyle ls = skin.get(Label.LabelStyle.class);
        ls.font = Fonts.font;
        ls.fontColor = Color.WHITE;
        skin.get(TextField.TextFieldStyle.class).font = Fonts.font;
        SelectBox.SelectBoxStyle sbs = skin.get(SelectBox.SelectBoxStyle.class);
        sbs.font = Fonts.font;
        sbs.listStyle.font = Fonts.font;
        clearColor = skinStrings.skinConfig.clearColor;
    }

    /** @return `true` if the screen is higher than it is wide */
    public boolean isPortrait() {
        return stage.getViewport().getScreenHeight() > stage.getViewport().getScreenWidth();
    }

    /** @return `true` if the screen is higher than it is wide _and_ resolution is at most 1050x700 */
    public boolean isCrampedPortrait() {
        return isPortrait() && game.settings.getScreenSize().getVirtualHeight() <= 700;
    }

    /** @return `true` if the screen is narrower than 4:3 landscape */
    public boolean isNarrowerThan4to3() {
        return Scene2dExtensions.isNarrowerThan4to3(stage);
    }

    public void openOptionsPopup(int startingPage = OptionsPopup.defaultPage, onClose: () -> Unit = {}) {
        OptionsPopup(this, startingPage, onClose).open(force = true);
    }
}
