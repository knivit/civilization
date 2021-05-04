package com.tsoft.civilization.economic;

import java.util.function.BiFunction;

class StringParser<T> {

    public T parse(String str, T initialValue, BiFunction<Character, Integer, T> creator, BiFunction<T, T, T> adder) {
        T val = initialValue;

        int i = 0;
        char type = '\0';
        boolean typeMode = true;
        while (i < str.length()) {
            if (typeMode) {
                type = str.charAt(i);
                if (type != ' ') typeMode = false;
                i ++;

                continue;
            }

            int k = i + 1;
            int sign = 1;
            if (k < str.length() && str.charAt(k) == '-') {
                sign = -1;
                k ++;
            }

            while (k < str.length() && str.charAt(k) >= '0' && str.charAt(k) <= '9') k++;

            int value = sign * Integer.parseInt(str.substring(i, k));
            i = k;
            typeMode = true;

            T newVal = creator.apply(type, value);
            val = adder.apply(val, newVal);
        }

        return val;
    }
}
