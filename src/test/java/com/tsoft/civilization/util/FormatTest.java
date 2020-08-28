package com.tsoft.civilization.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatTest {

    @Test
    public void text() {
        assertEquals("1b", Format.text("ab", "a", 1).toString());
        assertEquals("12", Format.text("ab", "a", 1, "b", 2).toString());
        assertEquals("", Format.text("ab", "ab", "").toString());
    }

    @Test
    public void textError() {
        assertThrows(IllegalArgumentException.class, () -> Format.text("ab", "ab", 1, "b", 2));
    }
}
