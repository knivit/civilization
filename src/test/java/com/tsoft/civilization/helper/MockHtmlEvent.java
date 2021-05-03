package com.tsoft.civilization.helper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockHtmlEvent {

    private final String action;
    private final JsonNode args;
}
