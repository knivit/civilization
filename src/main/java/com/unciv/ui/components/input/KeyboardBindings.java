package com.unciv.ui.components.input;

import java.util.HashMap;

public class KeyboardBindings extends HashMap<KeyboardBinding, KeyCharAndCode> {

    /** Allows adding entries by [KeyboardBinding] as name / [KeyCharAndCode] as string representation */
    public void put(String name, String value) {
        KeyboardBinding binding = KeyboardBinding.findByName(name);
        if (binding != null) {
            put(binding, value);
        }
    }

    /** Allows adding entries by [KeyCharAndCode] string representation,
     *  an empty [value] resets the binding to default */
    public void put(KeyboardBinding binding, String value) {
        if (value.isEmpty()) {
            remove(binding);
        } else {
            KeyCharAndCode key = KeyCharAndCode.parse(value);
            if (key != KeyCharAndCode.UNKNOWN) {
                put(binding, key);
            }
        }
    }

    /**
     * Adds or replaces a binding or removes it if [value] is the default for [key]
     * @param key the map key defining the binding
     * @param value the keyboard key to assign
     * @return the previously bound key if any
     */
    // Note clearer parameter names gives "PARAMETER_NAME_CHANGED_ON_OVERRIDE" warnings
    @Override
    public KeyCharAndCode put(KeyboardBinding key, KeyCharAndCode value) {
        KeyCharAndCode result = super.get(key);
        if (key.getDefaultKey().equals(value)) {
            remove(key);
        } else {
            super.put(key, value);
        }
        return result;
    }

    /** Indexed access will return default key for missing entries */
    public KeyCharAndCode get(KeyboardBinding key) {
        KeyCharAndCode result = super.get(key);
        return (result == null) ? key.getDefaultKey() : result;
    }
}
