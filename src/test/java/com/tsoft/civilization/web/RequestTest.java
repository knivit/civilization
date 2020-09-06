package com.tsoft.civilization.web;

import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.request.RequestReader;
import com.tsoft.civilization.web.request.RequestType;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    private final RequestReader requestReader = new RequestReader();

    @Test
    public void readGetHeaders1() {
        BufferedReader is = new BufferedReader(new StringReader(
            "GET /\r\n"
        ));
        Request request = requestReader.readRequest("127.0.0.1", 80, is);

        assertEquals(RequestType.GET, request.getRequestType());
        assertEquals("", request.getRequestUrl());
    }

    @Test
    public void readGetHeaders2() {
        BufferedReader is = new BufferedReader(new StringReader(
            "GET /GetMeTestPage.html\r\n"
        ));
        Request request = requestReader.readRequest("127.0.0.1", 80, is);

        assertEquals(RequestType.GET, request.getRequestType());
        assertEquals("GetMeTestPage.html", request.getRequestUrl());
    }

    @Test
    public void readPostHeaders1() {
        BufferedReader is = new BufferedReader(new StringReader(
            "POST /GetTileStatus\n" +
            "Content-Length:11\n" +
            "\n" +
            "col=2&row=3"
        ));
        Request request = requestReader.readRequest("127.0.0.1", 80, is);

        assertEquals(RequestType.POST, request.getRequestType());
        assertEquals("GetTileStatus", request.getRequestUrl());
        assertEquals("2", request.get("col"));
        assertEquals("3", request.get("row"));
    }
}
