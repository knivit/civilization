package com.tsoft.civilization.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class StringParser {

    public Map<String, Integer> parse(String str, Set<String> availableIdentifiers) {
        Map<String, Integer> result = new HashMap<>();
        AtomicInteger pos = new AtomicInteger(0);

        while (pos.get() < str.length()) {
            skipBlank(str, pos);

            String identifier = readIdentifier(str, pos);
            if (!availableIdentifiers.contains(identifier)) {
                throw new IllegalArgumentException("Unknown identifier " + identifier);
            }

            skipBlank(str, pos);

            skipSetter(str, pos);

            skipBlank(str, pos);

            int value = readValue(str, pos);

            result.put(identifier, value);
        }

        return result;
    }

    private int readValue(String str, AtomicInteger pos) {
        int value = 0;

        int sign = isSign(str, pos) ? -1 : 1;

        while (pos.get() < str.length() && Character.isDigit(str.charAt(pos.get()))) {
            value = (value * 10) + (str.charAt(pos.get()) - '0');
            pos.incrementAndGet();
        }

        return value * sign;
    }

    private boolean isSign(String str, AtomicInteger pos) {
        return pos.get() < str.length() && str.charAt(pos.get()) == '-';
    }

    private String readIdentifier(String str, AtomicInteger pos) {
        StringBuilder buf = new StringBuilder();

        while (pos.get() < str.length() && Character.isAlphabetic(str.charAt(pos.get()))) {
            buf.append(str.charAt(pos.get()));
            pos.incrementAndGet();
        }

        return buf.toString();
    }

    private void skipBlank(String str, AtomicInteger pos) {
        while (pos.get() < str.length() && Character.isWhitespace(str.charAt(pos.get()))) {
            pos.incrementAndGet();
        }
    }

    private void skipSetter(String str, AtomicInteger pos) {
        if (pos.get() < str.length() && str.charAt(pos.get()) == ':') {
            pos.incrementAndGet();
        }
    }
}
