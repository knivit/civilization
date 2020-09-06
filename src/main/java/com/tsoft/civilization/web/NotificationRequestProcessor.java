package com.tsoft.civilization.web;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.event.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationRequestProcessor {
    private NotificationRequestProcessor() { }

    public static void processRequest(ServerClient client) {
        ClientSession session = client.getSession();
        if (session == null) {
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
        Thread.currentThread().setName("Notifications for " + client.getRequest().toString());

        boolean needWorldUpdate = false;
        boolean needControlPanelUpdate = false;
        boolean needStatusPanelUpdate = false;

        World world = myCivilization.getWorld();

        int lastEventId = NumberUtil.parseInt(client.getRequest().getHeader("Last-Event-ID"), -1);
        log.debug("Notifications for {} are started from {}",  client.getRequest(), lastEventId);

        while (true) {
            // Waiting for events
            if (lastEventId >= (myCivilization.getEvents().size() - 1)) {
                // Update the World's map
                if (needWorldUpdate) {
                    if (!sendUpdateWorldEvent(client, world)) {
                        return;
                    }
                    needWorldUpdate = false;
                }

                // Update the Control Panel
                if (needControlPanelUpdate) {
                    if (!sendUpdateControlPanelEvent(client)) {
                        return;
                    }
                    needControlPanelUpdate = false;
                }

                // Update the Status Panel
                if (needStatusPanelUpdate) {
                    if (!sendUpdateStatusPanelEvent(client)) {
                        return;
                    }
                    needStatusPanelUpdate = false;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Sending HTTP 400 will stop other requests from EventSource
                    client.sendError(L10nServer.SERVER_ERROR);
                    return;
                }
                continue;
            }

            // Send the next event
            lastEventId ++;

            Event event = myCivilization.getEvents().get(lastEventId);
            if (event.isUpdateWorldEvent()) {
                needWorldUpdate = true;
            }

            if (event.isUpdateControlPanelEvent()) {
                needControlPanelUpdate = true;
            }

            if (event.isUpdateStatusPanelEvent()) {
                needStatusPanelUpdate = true;
            }

            if (!sendInformationEvent(client, event, lastEventId)) {
                return;
            }
        }
    }

    private static boolean sendInformationEvent(ServerClient client, Event event, int lastEventId) {
        String data = event.getJSON().getText();

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

    private static boolean sendUpdateWorldEvent(ServerClient client, World world) {
        StringBuilder msg = Format.text(
            "event: updateWorld\n" +
            "data: $data\n\n",

            "$data", world.getView().getJSON().getText()
        );

        return client.sendText(msg.toString());
    }

    private static boolean sendUpdateControlPanelEvent(ServerClient client) {
        StringBuilder msg = Format.text(
            "event: updateControlPanel\n" +
            "data: \n\n"
        );

        return client.sendText(msg.toString());
    }


    private static boolean sendUpdateStatusPanelEvent(ServerClient client) {
        return true;
    }
}
