package com.unciv.utils;

public class Kotlin {

    public static int coerceIn(int value, int minimumValue, int maximumValue) {
        if (minimumValue > maximumValue)
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum $maximumValue is less than minimum $minimumValue.");

        if (value < minimumValue) return minimumValue;
        if (value > maximumValue) return maximumValue;
        return value;
    }
}
