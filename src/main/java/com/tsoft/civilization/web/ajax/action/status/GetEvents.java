package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nEvent;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.event.Event;
import com.tsoft.civilization.world.event.EventList;

import static com.tsoft.civilization.L10n.L10nServer.INVALID_REQUEST;

public class GetEvents extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // Get the year's index in World.years list
        int stepNo = NumberUtil.parseInt(request.get("year"), -1);
        if (stepNo == -1) {
            return Response.newErrorInstance(INVALID_REQUEST);
        }

        if (stepNo > civilization.getYear().getStepNo()) {
            stepNo = civilization.getStartYear().getStepNo();
        }
        Year year = civilization.getWorld().getYears().get(stepNo);

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$eventsNavigation\n" +
            "$events\n",

            "$navigationPanel", getNavigationPanel(),
            "$eventsNavigation", getEventsNavigation(civilization, year, stepNo),
            "$events", getEvents(civilization, year));
        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    private StringBuilder getEventsNavigation(Civilization civilization, Year year, int stepNo) {
        StringBuilder priorButton = null;
        if ((stepNo - 1) >= civilization.getStartYear().getStepNo()) {
            priorButton = Format.text(
                "<button onclick=\"client.getEvents({ year:'$year' })\">$priorButton</button>",

                "$year", (stepNo - 1),
                "$priorButton", L10nEvent.PRIOR_YEAR_BUTTON
            );
        }

        StringBuilder nextButton = null;
        if ((stepNo + 1) <= civilization.getYear().getStepNo()) {
            nextButton = Format.text(
                "<button onclick=\"client.getEvents({ year:'$year' })\">$nextButton</button>",

                "$year", (stepNo + 1),
                "$nextButton", L10nEvent.NEXT_YEAR_BUTTON
            );
        }

        return Format.text(
            "<table id='paging_panel'>" +
                "<tr>" +
                    "<td>$priorButton</td>" +
                    "<td>$year</td>" +
                    "<td>$nextButton</td>" +
                "</tr>" +
            "</table>",

            "$priorButton", priorButton,
            "$year", year.getYearLocalized(),
            "$nextButton", nextButton
        );
    }

    private StringBuilder getEvents(Civilization civilization, Year year) {
        EventList events = civilization.getEvents().getEventsForYear(year);
        if (events == null || events.size() == 0) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (Event event : events) {
            buf.append(Format.text(
                "<tr><td>$serverTime<br>$description</td></tr>",

                "$serverTime", event.getServerEventTime(),
                "$description", event.getLocalized()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th>$header</th></tr>" +
                "$events" +
            "</table>",

            "$header", L10nEvent.EVENT_TABLE_HEADER,
            "$events", buf
        );
    }
}
