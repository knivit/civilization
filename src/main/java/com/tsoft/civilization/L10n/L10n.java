package com.tsoft.civilization.L10n;

import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;

import java.util.HashMap;
import java.util.Map;

public class L10n {

    public static final String DEFAULT_LANGUAGE = "en";

    private final Map<String, String> map = new HashMap<>();

    public L10n put(String key, String value) {
        map.put(key, value);
        return this;
    }

    public String getLocalized() {
        ClientSession session = Sessions.getCurrent();
        String language = DEFAULT_LANGUAGE;
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
            return Format.text(value, params).toString();
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
        return map.get((language == null) ? DEFAULT_LANGUAGE : language);
    }

    public boolean isEnglishEquals(L10n other) {
        return other != null && getEnglish().equals(other.getEnglish());
    }

    @Override
    public String toString() {
        return getLocalized();
    }
}
