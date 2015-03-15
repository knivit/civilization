package com.tsoft.civilization.L10n;

import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;

import java.util.HashMap;

public class L10nMap extends HashMap<String, String> {
    public L10nMap set(String key, String value) {
        put(key, value);
        return this;
    }

    public String getLocalized() {
        ClientSession session = Sessions.getCurrent();
        String language = "en";
        if (session != null) {
            language = session.getLanguage();
        }

        String value = getValue(language);
        if (value == null && !"en".equals(language)) {
            return getEnglish();
        }

        return value;
    }

    public String getLocalized(Object ... params) {
        String value = getLocalized();
        if (value != null && params != null) {
            return String.format(value, params);
        }
        return value;
    }

    public String getEnglish() {
        return getValue("en");
    }

    public String getEnglish(Object ... params) {
        String value = getEnglish();
        if (value != null && params != null) {
            return String.format(value, params);
        }
        return value;
    }

    public String getValue(String language) {
        String value = super.get(language);
        return value;
    }

    public boolean isEnglishEquals(L10nMap other) {
        if (other != null && getEnglish().equals(other.getEnglish())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getLocalized();
    }
}
