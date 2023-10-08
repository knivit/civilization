package com.unciv.ui.popups;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.tsoft.civilization.game.logic.event.EventReceiver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Base class for all Popups, i.e. Tables that get rendered in the middle of a screen and on top of everything else
 *
 * @property stageToShowOn The stage that will be used for [open], measurements or finding other instances
 * @param scrollable Controls how content can scroll if too large - see [Scrollability]
 * @param maxSizePercentage Causes [topTable] to limit its height - useful if `scrollable` is on. Will be multiplied by stageToShowOn.height.
 */
public class Popup extends Table {

    Stage stageToShowOn;
    Scrollability scrollable = Scrollability.WithoutButtons;
    float maxSizePercentage = 0.9f;

    public Popup(BaseScreen screen) {
        super(screen.stage, scrollable);
    }

    constructor(
            screen: BaseScreen,
            scrollable: Scrollability = Scrollability.WithoutButtons,
            maxSizePercentage: Float = 0.9f
    ) : this(screen.stage, scrollable, maxSizePercentage)

    private float maxPopupWidth = stageToShowOn.getWidth() * maxSizePercentage;
    private float maxPopupHeight = stageToShowOn.getHeight() * maxSizePercentage;

    /** This exists to differentiate the actual popup (this table)
     *  from the 'screen blocking' part of the popup (which covers the entire screen).
     *
     *  Note you seldom need to interact directly with it, Popup has many Table method proxies
     *  that pass through, like [add], [row], [defaults], [addSeparator] or [clear].
     */
    /* Hierarchy:
        Scrollability.None:
        * Stage
            * this@Popup (fills parent, catches click-behind)
                * innerTable (entire Popup content, smaller, limited by maxSizePercentage)
                    * topTable and bottomTable _reference_ innerTable
        Scrollability.All:
        * Stage
            * this@Popup (fills parent, catches click-behind)
                * ScrollPane (anonymous)
                    * innerTable (entire Popup content, smaller, limited by maxSizePercentage)
                        * topTable and bottomTable _reference_ innerTable
        Scrollability.WithoutButtons:
        * Stage
            * this@Popup (fills parent, catches click-behind)
                * innerTable (entire Popup content, smaller, limited by maxSizePercentage)
                    * ScrollPane (anonymous)
                        * topTable
                    * bottomTable
    */
    protected Table innerTable = new Table(BaseScreen.skin);

    /** This contains most of the Popup content (except the closing buttons which go in [bottomTable]) */
    private Table topTable;
    private Cell<WidgetGroup> topTableCell;

    /** This contains the bottom row buttons and does not participate in scrolling */
    protected Table bottomTable;

    /** Callbacks that will be called whenever this Popup is shown */
    List<Supplier<Void>> showListeners = new ArrayList<>();
    /** Callbacks that will be called whenever this Popup is closed, no matter how (e.g. no distinction OK/Cancel) */
    List<Supplier<Void>> closeListeners = new ArrayList<>();

    /** [EventBus] is used to receive [UncivStage.VisibleAreaChanged] */
    protected EventReceiver events = new EventReceiver();

    /** Enables/disables closing by clicking/tapping outside [innerTable].
     *
     *  Automatically set when [addCloseButton] is called but may be changed back or enabled without such a button.
     */
    protected boolean clickBehindToClose = false;

    /** Unlike [closeListeners] this is only fired on "click-behind" closing */
    protected Supplier<Void> onCloseCallback;

    init {
        // Set actor name for debugging
        name = javaClass.simpleName

        background = BaseScreen.skinStrings.getUiBackground(
                "General/Popup/Background",
                tintColor = Color.GRAY.cpy().apply { a = 0.5f })

        //todo topTable and bottomTable _could_ be separately skinnable - but would need care so rounded edges work
        innerTable.background = BaseScreen.skinStrings.getUiBackground(
                "General/Popup/InnerTable",
                tintColor = BaseScreen.skinStrings.skinConfig.baseColor.darken(0.5f)
        )
        innerTable.touchable = Touchable.enabled

        fun wrapInScrollPane(table: Table) = AutoScrollPane(table, BaseScreen.skin)
                .apply { setOverscroll(false, false) }
        when (scrollable) {
            Scrollability.None -> {
                topTable = innerTable
                bottomTable = innerTable
                topTableCell = super.add(innerTable)
            }
            Scrollability.All -> {
                topTable = innerTable
                bottomTable = innerTable
                topTableCell = super.add(wrapInScrollPane(innerTable))
            }
            Scrollability.WithoutButtons -> {
                topTable = Table(BaseScreen.skin)
                topTable.pad(20f).padBottom(0f)
                topTable.defaults().fillX().pad(5f)
                bottomTable = Table(BaseScreen.skin)
                topTableCell = innerTable.add(wrapInScrollPane(topTable))
                innerTable.defaults().fillX()
                innerTable.row()
                innerTable.add(bottomTable)
                super.add(innerTable)
            }
        }

        bottomTable.pad(20f)
        bottomTable.defaults().pad(5f)
        topTableCell.maxSize(maxPopupWidth, maxPopupHeight)

        isVisible = false
        touchable = Touchable.enabled
        // clicking behind gets special treatment
        super.addListener(getBehindClickListener())
        super.setFillParent(true)
    }

    private void recalculateInnerTableMaxHeight() {
        if (topTable == bottomTable) return;
        topTableCell.maxHeight(maxPopupHeight - bottomTable.getPrefHeight());
        innerTable.invalidate();
    }

    /**
     * Displays the Popup on the screen. If another popup is already open, this one will display after the other has
     * closed. Use [force] = true if you want to open this popup above the other one anyway.
     */
    void open(boolean force) {
        stageToShowOn.addActor(this);
        recalculateInnerTableMaxHeight();
        innerTable.pack();
        pack();
        center(stageToShowOn);
        events.receive(UncivStage.VisibleAreaChanged::class) {
            fitContentIntoVisibleArea(it.visibleArea);
        }
        fitContentIntoVisibleArea((stageToShowOn as UncivStage).lastKnownVisibleArea);
        if (force || !stageToShowOn.hasOpenPopups()) {
            show();
        }
    }

    private void fitContentIntoVisibleArea(Rectangle visibleArea) {
        padLeft(visibleArea.x);
        padBottom(visibleArea.y);
        padRight(stageToShowOn.getWidth() - visibleArea.x - visibleArea.width);
        padTop(stageToShowOn.getHeight() - visibleArea.y - visibleArea.height);
        invalidate();
    }

    /** Subroutine for [open] handles only visibility */
    private void show() {
        this.isVisible = true;
        for (Supplier<Void> listener : showListeners) {
            listener.get();
        }
    }

    /**
     * Close this popup and - if any other popups are pending - display the next one.
     */
    void close() {
        events.stopReceiving();
        for (Supplier<Void> listener : closeListeners) {
            listener.get();
        }
        remove();
        Popup nextPopup = stageToShowOn.actors.firstOrNull { it is Popup }
        if (nextPopup != null) (nextPopup as Popup).show();
    }

    /** Allow closing a popup by clicking 'outside', Android-style, but only if a Close button exists */
    private fun getBehindClickListener() = object : ClickListener() {
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            if (!clickBehindToClose) return;
            // Since Gdx doesn't limit events to the actually `hit` actors...
            if (event?.target != this@Popup) return;
                    close();
            onCloseCallback?.invoke();
        }
    }

    /* All additions to the popup are to the inner table - we shouldn't care that there's an inner table at all.
       Note the Kdoc mentions innerTable when under Scrollability.WithoutButtons it's actually topTable,
       but metioning that distinction seems overkill. innerTable has the clearer Kdoc for "where the Actors go".
    */
    /** Popup proxy redirects [add][com.badlogic.gdx.scenes.scene2d.ui.Table.add] to [innerTable] */
    @Override
    public <T extends Actor> Cell<T> add(T actor) { return topTable.add(actor); }

    /** Popup proxy redirects [add][com.badlogic.gdx.scenes.scene2d.ui.Table.add] to [innerTable] */
    @Override
    public Cell<Actor> add() { return topTable.add(); }

    /** Popup proxy redirects [row][com.badlogic.gdx.scenes.scene2d.ui.Table.row] to [innerTable] */
    @Override
    public Cell<Actor> row() { return topTable.row(); }

    /** Popup proxy redirects [defaults][com.badlogic.gdx.scenes.scene2d.ui.Table.defaults] to [innerTable] */
    @Override
    public Cell<Actor> defaults() { return topTable.defaults(); }

    /** Popup proxy redirects [addSeparator][com.unciv.ui.components.extensions.addSeparator] to [innerTable] */
    void addSeparator(Color color, int colSpan, float height) {
        topTable.addSeparator(color, colSpan, height);
    }
    /** Proxy redirects [add][com.badlogic.gdx.scenes.scene2d.ui.Table.clear] to clear content:
     *  [innerTable] or if [Scrollability.WithoutButtons] was used [topTable] and [bottomTable] */
    override fun clear() {
        topTable.clear()
        bottomTable.clear()
        clickBehindToClose = false
        onCloseCallback = null
    }


    /**
     * Adds a [caption][text] label: A label with word wrap enabled over half the stage width.
     * Will be larger than normal text if the [size] parameter is set to > [Constants.defaultFontSize].
     * @param text The caption text.
     * @param size The font size for the label.
     */
    fun addGoodSizedLabel(text: String, size: Int = Constants.defaultFontSize, hideIcons:Boolean = false): Cell<Label> {
        val label = text.toLabel(fontSize = size, hideIcons = hideIcons)
        label.wrap = true
        label.setAlignment(Align.center)
        return add(label).width(stageToShowOn.width / 2)
    }

    /**
     * Adds a [TextButton].
     * @param text The button's caption.
     * @param key Associate a key with this button's action.
     * @param action A lambda to be executed when the button is clicked.
     * @return The new [Cell]
     */
    fun addButton(
            text: String,
            key: KeyCharAndCode? = null,
            style: TextButtonStyle? = null,
            action: () -> Unit
    ): Cell<TextButton> {
        val button = text.toTextButton(style)
        button.onActivation { action() }
        button.keyShortcuts.add(key)
        return bottomTable.add(button)
    }
    fun addButton(text: String, key: Char, style: TextButtonStyle? = null, action: () -> Unit)
            = addButton(text, KeyCharAndCode(key), style, action)
    @Suppress("unused")  // Keep the offer to pass an Input.keys value
    fun addButton(text: String, key: Int, style: TextButtonStyle? = null, action: () -> Unit)
            = addButton(text, KeyCharAndCode(key), style, action)
    fun addButton(text: String, binding: KeyboardBinding, style: TextButtonStyle? = null, action: () -> Unit): Cell<TextButton> {
        val button = text.toTextButton(style)
        button.onActivation(binding = binding) { action() }
        return bottomTable.add(button)
    }

    /**
     * Adds a [TextButton] that closes the popup, with [BACK][KeyCharAndCode.BACK] already mapped.
     * @param text The button's caption, defaults to "Close".
     * @param additionalKey An additional key that should act like a click.
     * @param action A lambda to be executed after closing the popup when the button is clicked.
     * @return The new [Cell]
     */
    fun addCloseButton(
            text: String = Constants.close,
            additionalKey: KeyCharAndCode? = null,
            style: TextButtonStyle? = null,
            action: (()->Unit)? = null
            ): Cell<TextButton> {
        clickBehindToClose = true
        onCloseCallback = action
        val cell = addButton(text, additionalKey, style) {
            close()
            action?.invoke()
        }
        cell.actor.keyShortcuts.add(KeyCharAndCode.BACK)
        return cell
    }

    /**
     * Adds a [TextButton] that can close the popup, with [RETURN][KeyCharAndCode.RETURN] already mapped.
     * @param text The button's caption, defaults to "OK".
     * @param additionalKey An additional key that should act like a click.
     * @param validate Function that should return true when the popup can be closed and `action` can be run.
     * When this function returns false, nothing happens.
     * @param action A lambda to be executed after closing the popup when the button is clicked.
     * @return The new [Cell]
     */
    fun addOKButton(
            text: String = Constants.OK,
            additionalKey: KeyCharAndCode? = null,
            style: TextButtonStyle? = null,
            validate: (() -> Boolean) = { true },
    action: (() -> Unit),
            ): Cell<TextButton> {
        val cell = addButton(text, additionalKey, style) {
            if (validate()) {
                close()
                action()
            }
        }
        cell.actor.keyShortcuts.add(KeyCharAndCode.RETURN)
        return cell
    }

    /** Overload of [addCloseButton] accepting a bindable key definition as [additionalKey] */
    fun addCloseButton(text: String, additionalKey: KeyboardBinding, action: (() -> Unit)? = null) =
    addCloseButton(text, KeyboardBindings[additionalKey], action = action)
    /** Overload of [addOKButton] accepting a bindable key definition as [additionalKey] */
    fun addOKButton(text: String, additionalKey: KeyboardBinding, style: TextButtonStyle? = null, action: () -> Unit) =
    addOKButton(text, KeyboardBindings[additionalKey], style, action = action)

    /**
     * The last two additions ***must*** be buttons.
     * Make their width equal by setting minWidth of one cell to actor width of the other.
     */
    fun equalizeLastTwoButtonWidths() {
        val n = bottomTable.cells.size
        if (n < 2) throw UnsupportedOperationException()
        val cell1 = bottomTable.cells[n-2]
        val cell2 = bottomTable.cells[n-1]
        if (cell1.actor !is Button || cell2.actor !is Button) throw UnsupportedOperationException()
        cell1.minWidth(cell2.actor.width).uniformX()
        cell2.minWidth(cell1.actor.width).uniformX()
    }

    /**
     * Reuse this popup as an error/info popup with a new message.
     * Removes everything from the popup to replace it with the message
     * and a close button if requested
     */
    fun reuseWith(newText: String, withCloseButton: Boolean = false) {
        clear()
        addGoodSizedLabel(newText)
        if (withCloseButton) {
            addCloseButton()
        }
    }

    /**
     * Sets or retrieves the [Actor] that currently has keyboard focus.
     *
     * Setting focus on a [TextField] will select all contained text unless a
     * [FocusListener][com.badlogic.gdx.scenes.scene2d.utils.FocusListener] cancels the event.
     */
    var keyboardFocus: Actor?
    get() = stageToShowOn.keyboardFocus
    set(value) {
        if (stageToShowOn.setKeyboardFocus(value))
            (value as? TextField)?.selectAll()
    }

    /** Gets the ScrollPane the content is wrapped in (only if Popup was instantiated with scrollable=true) */
    fun getScrollPane() = topTable.parent as? ScrollPane
}


    /** @return A [List] of currently active or pending [Popup] screens. */
    val BaseScreen.popups
        get() = stage.popups
private val Stage.popups: List<Popup>
    get() = actors.filterIsInstance<Popup>()

/** @return The currently active [Popup] or `null` if none. */
        val BaseScreen.activePopup: Popup?
        get() = popups.lastOrNull { it.isVisible }

/**
 * Checks if there are visible [Popup]s.
 * @return `true` if any were found.
 */
        fun BaseScreen.hasOpenPopups(): Boolean = stage.hasOpenPopups()
private fun Stage.hasOpenPopups(): Boolean = actors.any { it is Popup && it.isVisible }

/** Closes all [Popup]s. */
        fun BaseScreen.closeAllPopups() = popups.forEach { it.close() }
