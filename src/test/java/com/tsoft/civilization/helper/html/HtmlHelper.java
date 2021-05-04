package com.tsoft.civilization.helper.html;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.helper.MockHtmlEvent;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class HtmlHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HtmlHelper() { }

    public static HtmlDocument parse(String data) {
        int i = 0;
        while (i < data.length()) {
            while (i < data.length() && data.charAt(i) == ' ') {
                i ++;
            }

            if (i < data.length() && data.charAt(i) != '<') {
                throw new IllegalStateException("Expected '<' at position = " + i + ", data = " + data);
            }
            int tagNameStart = i + 1;

            while (i < data.length() && data.charAt(i) != '>') {
                i ++;
            }

            if (i < data.length() && data.charAt(i) != '>') {
                throw new IllegalStateException("Expected '>' at position = " + i + ", data = " + data);
            }
            int tagNameEnd = i;

            String tagName = data.substring(tagNameStart, tagNameEnd);

            String tagEnd = "</" + tagName + ">";

            while (i < data.length() - tagEnd.length() && data.startsWith(tagEnd, i)) {
                i ++;
            }
        }
        return HtmlDocument.builder().build();
    }

    public static MockHtmlEvent extractEvent(String text) {
        int from = text.indexOf('(');

        if (from > 0) {
            int to = text.indexOf(')');
            return MockHtmlEvent.builder()
                .action(text.substring(0, from).trim())
                .args(readTree(text.substring(from + 1, to)))
                .build();
        }

        return MockHtmlEvent.builder()
            .action(text.trim())
            .build();
    }

    public static Set<Point> extractLocations(JsonNode node) {
        Set<Point> result = new HashSet<>();
        for (Iterator<JsonNode> it = node.elements(); it.hasNext(); ) {
            JsonNode elem = it.next();

            Point p = new Point(elem.get("col").asInt(), elem.get("row").asInt());
            result.add(p);
        }
        return result;
    }

    private static JsonNode readTree(String text) {
        try {
            return objectMapper.readTree(text);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
