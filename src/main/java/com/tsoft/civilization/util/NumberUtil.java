package com.tsoft.civilization.util;

public class NumberUtil {
    public static int parseInt(String value, int onErrorValue) {
        if (value == null || value.length() == 0) {
            return onErrorValue;
        }

        int val;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            val = onErrorValue;
        }
        return val;
    }
}
