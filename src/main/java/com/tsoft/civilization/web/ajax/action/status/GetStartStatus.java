package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GetStartStatus extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        String[] images = new String[] {
            "images/logos/borobudur.jpg",
            "images/logos/castle.jpg"
        };
        int n = ThreadLocalRandom.current().nextInt(2);

        String value = new StringBuilder(String.format(
            "<table id='title_table'>" +
                "<tr><td><img src='%1$s'/></td></tr>" +
                "<tr><td>%2$s</td></tr>" +
            "</table>",
            images[n], L10nServer.WELCOME
        )).toString();
        return new Response(ResponseCode.OK, value, ContentType.TEXT_HTML);
    }
}
