package com.tsoft.civilization.web;

import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.RequestType;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    @Test
    public void readGetHeaders1() {
        BufferedReader is = new BufferedReader(new StringReader(
            "GET /\r\n"
        ));
        Request request = new Request("127.0.0.1", 80);
        request.readHeaders(is);
        request.readPostData(is);

        assertEquals(RequestType.GET, request.getRequestType());
        assertEquals("", request.getRequestUrl());
    }

    @Test
    public void readGetHeaders2() {
        BufferedReader is = new BufferedReader(new StringReader(
            "GET /GetMeTestPage.html\r\n"
        ));
        Request request = new Request("127.0.0.1", 80);
        request.readHeaders(is);
        request.readPostData(is);

        assertEquals(RequestType.GET, request.getRequestType());
        assertEquals("GetMeTestPage.html", request.getRequestUrl());
    }

    @Test
    public void readPostHeaders1() {
        BufferedReader is = new BufferedReader(new StringReader(
           ("POST /GetTileStatus\n" +
            "Content-Length:11\n" +
            "\n" +
            "col=2&row=3")
        ));
        Request request = new Request("127.0.0.1", 80);
        request.readHeaders(is);
        request.readPostData(is);

        assertEquals(RequestType.POST, request.getRequestType());
        assertEquals("GetTileStatus", request.getRequestUrl());
        assertEquals(2, request.getParams().size());
        assertEquals("2", request.get("col"));
        assertEquals("3", request.get("row"));
    }
}
