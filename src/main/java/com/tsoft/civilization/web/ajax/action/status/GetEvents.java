package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.*;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.civilization.Civilization;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.tsoft.civilization.web.L10nServer.INVALID_REQUEST;

public class GetEvents extends AbstractAjaxRequest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("HH:mm:ss")
        .withZone(ZoneId.of("UTC"));

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private final GetCivilizationInfo civilizationInfo = new GetCivilizationInfo();

    public static StringBuilder getAjax(int year) {
        return Format.text("server.sendAsyncAjax('ajax/GetEvents', { year:'$year' })",
            "$year", year
        );
    }

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // Get the year's index in World.years list
        int stepNo = NumberUtil.parseInt(request.get("year"), -1);
        if (stepNo == -1) {
            return JsonResponse.badRequest(INVALID_REQUEST);
        }

        if (stepNo > civilization.getYear().getStepNo()) {
            stepNo = civilization.getStartYear().getStepNo();
        }

        Year year = civilization.getWorld().getHistory(stepNo);

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizationInfo
            $eventsNavigation
            $events
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$civilizationInfo", civilizationInfo.getContent(civilization),
            "$eventsNavigation", getEventsNavigation(civilization, year, stepNo),
            "$events", getEvents(civilization, year));

        return HtmlResponse.ok(value);
    }

    private StringBuilder getEventsNavigation(Civilization civilization, Year year, int stepNo) {
        StringBuilder priorButton = null;
        if ((stepNo - 1) >= civilization.getStartYear().getStepNo()) {
            priorButton = Format.text("""
                <button onclick="$getEvents">$priorButton</button>
                """,

                "$getEvents", ClientAjaxRequest.getEvents(stepNo - 1),
                "$priorButton", L10nEvent.PRIOR_YEAR_BUTTON
            );
        }

        StringBuilder nextButton = null;
        if ((stepNo + 1) <= civilization.getYear().getStepNo()) {
            nextButton = Format.text("""
                <button onclick="$getEvents">$nextButton</button>
                """,

                "$getEvents", ClientAjaxRequest.getEvents(stepNo + 1),
                "$nextButton", L10nEvent.NEXT_YEAR_BUTTON
            );
        }

        return Format.text("""
            <table id='paging_panel'>
                <tr>
                    <td>$priorButton</td>
                    <td>$year</td>
                    <td>$nextButton</td>
                </tr>
            </table>
            """,

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

                "$serverTime", DATE_TIME_FORMATTER.format(event.getServerTime()),
                "$description", event.getMessage().getLocalized(event.getArgs())
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th>$header</th></tr>
                $events
            </table>
            """,

            "$header", L10nEvent.EVENT_TABLE_HEADER,
            "$events", buf
        );
    }
}
