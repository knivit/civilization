package com.tsoft.civilization.util;

public final class ArrayUtil {

    private ArrayUtil() { }

    public static boolean allNull(Object ... elements) {
        if (elements == null) {
            return true;
        }

        for (Object element : elements) {
            if (element != null) {
                return false;
            }
        }

        return true;
    }
}
