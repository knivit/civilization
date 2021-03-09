package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;

import java.util.concurrent.ThreadLocalRandom;

public class GetStartStatus extends AbstractAjaxRequest {

    private static final String[] images = new String[] {
        "images/logos/borobudur.jpg",
        "images/logos/castle.jpg"
    };

    @Override
    public Response getJson(Request request) {
        int n = ThreadLocalRandom.current().nextInt(2);

        StringBuilder value = Format.text("""
            <table id='title_table'>
                <tr><td><img src='$image'/></td></tr>
                <tr><td>$text</td></tr>
            </table>
            """,

            images[n],
            L10nServer.WELCOME
        );

        return HtmlResponse.ok(value);
    }
}
