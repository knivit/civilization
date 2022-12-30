package com.tsoft.civilization.web;

import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.Event;
import com.tsoft.civilization.world.EventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationRequestProcessor {

    private static final String LAST_EVENT_ID_HEADER = "Last-Event-ID";

    private static final EventService eventService = new EventService();

    private NotificationRequestProcessor() { }

    public static void processRequest(Client client, Request request) {
        ClientSession session = Sessions.getCurrent();
        if (Sessions.getCurrent() == null) {
            // Sending HTTP 400 will stop other requests from EventSource
            client.sendError(L10nServer.INVALID_SESSION);
            return;
        }

        Civilization myCivilization = session.getCivilization();
        if (myCivilization == null) {
            // Sending HTTP 400 will stop other requests from EventSource
            client.sendError(L10nServer.CIVILIZATION_NOT_FOUND);
            return;
        }

        // Initial Response
        StringBuilder buf = Format.text(
            "HTTP/1.1 200 OK\r\n" +
            "Content-Type: $contentType\r\n" +
            "Cache-Control: no-cache" +
            "Connection': keep-alive",

            "$contentType", ContentType.NOTIFICATION_EVENT
        );
        if (!client.sendText(buf.toString())) {
            return;
        }

        // Change the name of this thread
        Thread.currentThread().setName("Notifications for " + request.toString());

        World world = myCivilization.getWorld();

        int sentEventId = NumberUtil.parseInt(request.getHeader(LAST_EVENT_ID_HEADER), -1);
        log.debug("Notifications for {} are started from eventId = {}",  request, sentEventId);

        while (true) {
            boolean needWorldUpdate = false;
            boolean needControlPanelUpdate = false;
            boolean needStatusPanelUpdate = false;

            // Send all information event to the client
            while ((sentEventId + 1) < (myCivilization.getEvents().size())) {
                sentEventId++;

                Event event = myCivilization.getEvents().get(sentEventId);
                log.debug("{}: notifying the client with event '{}'", sentEventId, event.getMessage().getLocalized(event.getArgs()));

                needWorldUpdate = needWorldUpdate || event.isUpdateWorldMap();
                needControlPanelUpdate = needControlPanelUpdate || event.isUpdateControlPanel();
                needStatusPanelUpdate = needStatusPanelUpdate || event.isUpdateStatusPanel();

                if (!sendInformationEvent(client, event, sentEventId)) {
                    return;
                }
            }

            // Send an update of the World's state
            if (needWorldUpdate && !sendUpdateWorldEvent(client, world)) {
                return;
            }

            // Update the Control Panel
            if (needControlPanelUpdate && !sendUpdateControlPanelEvent(client)) {
                return;
            }

            // Update the Status Panel
            if (needStatusPanelUpdate && !sendUpdateStatusPanelEvent(client)) {
                return;
            }

            // Waiting for the next batch of events
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Sending HTTP 400 will stop other requests from EventSource
                client.sendError(L10nServer.SERVER_ERROR);
                return;
            }
        }
    }

    private static boolean sendInformationEvent(Client client, Event event, int lastEventId) {
        String data = eventService.getJson(event).getText();

        // 'id' field must go in the end so a browser will update it after the data was received
        StringBuilder msg = Format.text(
            "data: $data\n\n" +
            "id: $lastEventId\n\n",

            "$data", data,
            "$lastEventId", lastEventId
        );

        // If the client doesn't listening, stop the pushing
        return client.sendText(msg.toString());
    }

    private static boolean sendUpdateWorldEvent(Client client, World world) {
        StringBuilder msg = Format.text(
            "event: updateWorld\n" +
            "data: $data\n\n",

            "$data", world.getView().getJson().getText()
        );

        return client.sendText(msg.toString());
    }

    private static boolean sendUpdateControlPanelEvent(Client client) {
        StringBuilder msg = Format.text(
            "event: updateControlPanel\n" +
            "data: \n\n"
        );

        return client.sendText(msg.toString());
    }

    private static boolean sendUpdateStatusPanelEvent(Client client) {
        return true;
    }
}
