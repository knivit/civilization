package com.unciv.ui.popups;

/** Controls how content may scroll.
 *
 * With scrolling enabled, the ScrollPane can be accessed via [getScrollPane].
 * @property None No scrolling
 * @property All Entire content wrapped in an [AutoScrollPane] so it can scroll if larger than maximum dimensions
 * @property WithoutButtons content separated into scrollable upper part and static lower part containing the buttons
 */
public enum Scrollability {

    None, All, WithoutButtons
}
