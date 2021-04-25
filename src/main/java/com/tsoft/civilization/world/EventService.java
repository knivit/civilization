package com.tsoft.civilization.world;

import com.tsoft.civilization.web.view.JsonBlock;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EventService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.of("UTC"));

    public JsonBlock getJson(Event event) {
        JsonBlock block = new JsonBlock();
        block.addParam("description", event.getMessage().getLocalized(event.getArgs()));
        block.addParam("serverTime", DATE_TIME_FORMATTER.format(event.getServerTime()));
        return block;
    }
}
