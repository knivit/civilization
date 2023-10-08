package com.unciv.ui.components.input;

import com.badlogic.gdx.Input;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class KeyCharAndCode {

    public static final KeyCharAndCode BACK = new KeyCharAndCode(Input.Keys.BACK);

    /** Automatically assigned for [BACK] */
    public static final KeyCharAndCode ESC = new KeyCharAndCode(Input.Keys.ESCAPE);

    /** Assigns [NUMPAD_ENTER] automatically as well */
    public static final KeyCharAndCode RETURN = new KeyCharAndCode(Input.Keys.ENTER);

    /** Automatically assigned for [RETURN] */
    public static final KeyCharAndCode NUMPAD_ENTER = new KeyCharAndCode(Input.Keys.NUMPAD_ENTER);
    public static final KeyCharAndCode SPACE = new KeyCharAndCode(Input.Keys.SPACE);
    public static final KeyCharAndCode DEL = new KeyCharAndCode(GdxKeyCodeFixes.DEL);
    public static final KeyCharAndCode TAB = new KeyCharAndCode(Input.Keys.TAB);

    /** Guaranteed to be ignored by [KeyShortcutDispatcher] and never to be generated for an actual event, used as fallback to ensure no action is taken */
    public static final KeyCharAndCode UNKNOWN = new KeyCharAndCode(Input.Keys.UNKNOWN);

    final private Character ch;
    final private Integer code;

    public KeyCharAndCode(Character ch) {
        this.ch = ch;
        code = null;
    }

    public KeyCharAndCode(Integer code) {
        this.code = code;
        ch = null;
    }

    public static KeyCharAndCode ctrl(Character ch) {
        return new KeyCharAndCode(ch);
    }

    public static KeyCharAndCode parse(String text) {
        if (text.length() == 1) {
            return new KeyCharAndCode(text.charAt(0));
        }

        if (text.length() == 3 && text.charAt(0) == '"' && text.charAt(2) == '"') {
            return new KeyCharAndCode(text.charAt(1));
        }

        if (text.length() == 6 && text.startsWith("Ctrl-")) {
            return new KeyCharAndCode(text.charAt(5));
        }

        if ("ESC".equals(text)) {
            return ESC;
        }

        Integer code = GdxKeyCodeFixes.valueOf(text);
        return (code == -1) ? UNKNOWN : new KeyCharAndCode(code);
    }
}
