package com.tsoft.civilization;

import com.tsoft.civilization.util.SystemProperties;
import com.tsoft.civilization.web.Server;

import static com.tsoft.civilization.util.SystemProperties.SERVER_PORT;

public class Main {
    public static void main(String[] args) throws Throwable {
        new SystemProperties().init();

        Server server = new Server(System.getProperty(SERVER_PORT, "9090"));
        server.start();
    }

/*

    "informationBoard" сделать светло-голубого цвета с белым шрифтом и отобразить по-очереди все полученные
    инфо-сообщения (Client.js) с задержкой в пару секунд; последнее также должно исчезнуть через пару секунд
        они как бы панелями появляются и исчезают после 3-го
*/
/**
    TODO

  After a click on 'Next Turn' show an alert if there are civilizations doing a move (AI can use some time to think)
  Clickable events
  Optimize map's redraw (currently - in client on button and on event redraw)
  Add params to L10nMap + NextMoveActionResults (L10nWorld.DECLARE_WAR_EVENT = must be 'A civilization X declared a war to civilization Y'

 + Fix multiply entries to client.onUpdateWorldResponse (from client and from server event)
 + 'Actions' table for a City doesn't have a header
 + New/capture city, new/destroy unit events must be sent to all civilization to map's redraw
 + Units movements must be sent to all civilizations also
 + City can't attack a foreign warriors (response from the server is "Unit not found')
 + Foreign warriors can't attack a city
 Event to refresh control panel when all civilizations moved

**/
}
