package com.tsoft.civilization.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.util.Point;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class HtmlHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HtmlHelper() { }

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
