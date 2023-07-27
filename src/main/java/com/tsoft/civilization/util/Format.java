package com.tsoft.civilization.util;

import java.util.Objects;

public final class Format {

    private Format() { }

    /**
     * Replaces "{}" placeholders in "... {} ..." with values
     */
    public static String format(String text, Object ... values) {
        Objects.requireNonNull(text, "text must not be null");
        Objects.requireNonNull(values, "values must not be null");

        int from;
        int n = 0;
        while ((from = text.indexOf('{')) >= 0) {
            if ((from >= (text.length() - 1)) || (text.charAt(from + 1) != '}')) {
                break;
            }

            if (n >= values.length) {
                throw new IllegalArgumentException("Invalid values: doesn't correspond to {}");
            }

            String value = toStr(values[n]);
            text = text.substring(0, from) + value + text.substring(from + 1);

            n ++;
        }

        return text;
    }

    /**
     * Replaces "$value" placeholders in "... $value ..." with values
     */
    public static StringBuilder text(String text, Object ... values) {
        Objects.requireNonNull(text, "text must not be null");
        Objects.requireNonNull(values, "values must not be null");

        // sort by param's name length by desc
        int[] index = new int[values.length / 2];
        for (int i = 0; i < index.length; i ++) {
            index[i] = i;
        }

        for (int i = 0; i < index.length; i ++) {
            for (int k = i + 1; k < index.length; k ++) {
                String name1 = (String)values[index[i] * 2];
                String name2 = (String)values[index[k] * 2];
                if (name2.compareTo(name1) > 0) {
                    int temp = index[i];
                    index[i] = index[k];
                    index[k] = temp;
                }
            }
        }

        // replace names with values
        StringBuilder buf = new StringBuilder(text);
        for (int i = 0; i < index.length; i ++) {
            String name = (String)values[index[i] * 2];
            String value = toStr(values[index[i] * 2 + 1]);

            for (int k = 1; k < 4; k ++) {
                int n = buf.indexOf(name);
                if (n == -1) {
                    if (k > 1) break;
                    throw new IllegalArgumentException("Parameter '" + name + "' is not found in the text:\n" + text);
                }

                buf.replace(n, n + name.length(), value);
            }
        }

        return buf;
    }

    private static String toStr(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
