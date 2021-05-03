package com.tsoft.civilization.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.civilization.web.response.Response;

import java.nio.charset.StandardCharsets;

public class JsonHelper {

    public static JsonNode parse(Response response) {
        try {
            return new ObjectMapper().readTree(new String(response.getContent(), StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
