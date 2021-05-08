"use strict";

var client = {
    worldUpdateInProgress: false,

    // Empty callback
    onEmptyResponse: function(response) {
        // do nothing, as response is empty
    },

    // Callback to redraw Status Panel
    onStatusResponse: function(response) {
        statusPanel.show(response);
    },

    // Callback to redraw Control Panel
    onControlPanelResponse: function(response) {
        controlPanel.show(response);
    },

    // Callback on load a world
    onLoadWorldResponse: function(response) {
        var world = JSON.parse(response);
        tilesMap.create(world.width, world.height, world.tiles);
        unitsMap.create(world.width, world.height, world.units);
        citiesMap.create(world.width, world.height, world.cities);

        drawMap.init();
        drawMap.scrollTo(world.selectedCol, world.selectedRow);

        redraw();
    },

    onUpdateWorldResponse: function(response) {
        if (client.worldUpdateInProgress) {
            return;
        }

        client.worldUpdateInProgress = true;
        try {
            var world = JSON.parse(response);
            tilesMap.update(world.tiles);
            unitsMap.update(world.units);
            citiesMap.update(world.cities);

            redraw();
        } finally {
            client.worldUpdateInProgress = false;
        }
    },

    // Callback to show a list of Worlds
    onGetWorldsResponse: function(response) {
        menuPanel.show(response);
    },

    // Callback to start a game after creation (or joining) to a World
    onResponseLoadGamePage: function(response) {
        var json = JSON.parse(response || "{}");
        window["startCol"] = json.col || 0;
        window["startRow"] = json.row || 0;

        window.location.href = "GamePage.html";
    },

    // Subscribe to get Notifications from the Server
    getNotifications: function() {
        var eventSource = new EventSource("GetNotifications");

        if (config.DEBUG_SERVER_NOTIFICATIONS) {
            eventSource.onopen = function(e) {
                console.log("EventSource connection established");
            };

            eventSource.onerror = function(e) {
                if (this.readyState == EventSource.CONNECTING) {
                    console.log("Connection is lost, trying to reconnect ...");
                } else {
                    console.log("Error: " + this.readyState);
                }
            };
        }

        eventSource.addEventListener('updateWorld', function(e) {
            if (config.DEBUG_SERVER_NOTIFICATIONS) {
                console.log("Got updateWorld event, data size =", e.data.length, "bytes");
            }

            client.onUpdateWorldResponse(e.data);
        }, false);

        eventSource.addEventListener('updateControlPanel', function(e) {
            if (config.DEBUG_SERVER_NOTIFICATIONS) {
                console.log("Got updateControlPanel event");
            }

            // GetControlPanel returns HTML (not JSON) so send standard Ajax-request to redraw the panel
            server.sendAsyncAjax('ajax/GetControlPanel', { }, client.onControlPanelResponse);
        }, false);

        eventSource.onmessage = function(e) {
            if (config.DEBUG_SERVER_NOTIFICATIONS) {
                console.log("Got information event:", e.data, ", lastEventId =", e.lastEventId);
            }

            //eventPanel.add(JSON.parse(e.data));
            document.getElementById("informationBoard").innerHTML = JSON.parse(e.data).description;
        };
    },

    // Events for a year
    getEvents: function(ajaxParams) {
        server.sendAsyncAjax('ajax/GetEvents', { "year": ajaxParams.year }, client.onStatusResponse);
    },

    // Start Pages

    // Select language
    onSelectLanguageRequest: function() {
        var language = document.getElementById('language').value;

        // The selector is on the GetWorldsRequest page, so reload it to change interface
        server.sendChainOfRequests([
            [ "ajax/SelectLanguageRequest", { "language": language }, client.onEmptyResponse ],
            [ "ajax/GetWorldsRequest", { }, client.onGetWorldsRequest ]
        ]);
    },

    // List of existing Worlds (to join or to create a new world)
    onGetWorldsRequest: function() {
        server.sendAsyncAjax('ajax/GetWorldsRequest', { }, client.onGetWorldsResponse);
    },

    // Get a form to create a new world
    onGetCreateWorldFormRequest: function() {
        server.sendAsyncAjax('ajax/GetCreateWorldFormRequest', { }, client.onGetWorldsResponse);
    },

    onCreateWorldRequest: function() {
        var worldName = document.getElementById('worldName').value;
        var mapConfiguration = document.getElementById('mapConfiguration').value;
        var mapSize = document.getElementById('mapSize').value;
        var climate = document.getElementById('climate').value;
        var difficultyLevel = document.getElementById('difficultyLevel').value;

        server.sendAsyncAjax('ajax/CreateWorldRequest',
           { "worldName": worldName,
             "mapConfiguration": mapConfiguration,
             "mapSize": mapSize,
             "climate": climate,
             "difficultyLevel": difficultyLevel
           }, client.onGetWorldsResponse);
    },

    onJoinWorldRequest: function(ajaxParams) {
        var civilization = document.getElementById(ajaxParams.civilizationSelector).value;

        server.sendAsyncAjax('ajax/JoinWorldRequest',
            { "world": ajaxParams.world,
              "civilization": civilization,
              "playerType": ajaxParams.playerType
            }, (ajaxParams.playerType === 'HUMAN') ? client.onResponseLoadGamePage : client.onGetWorldsResponse);
    },

    loadWorld: function() {
        server.sendChainOfRequests([
            [ "ajax/LoadWorldRequest", { }, client.onLoadWorldResponse ],
            [ "ajax/GetStartStatus", { }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    // On map's tile select event
    selectTile: function(col, row) {
        server.sendChainOfRequests([
            [ "ajax/GetTileStatus", { col: col, row: row }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    /** Button's on click events */

    /** Get Status */

    getUnitStatus: function(ajaxParams) {
        drawMap.makeTileInView(ajaxParams.col, ajaxParams.row);
        server.sendAsyncAjax('ajax/GetUnitStatus', { unit: ajaxParams.unit }, client.onStatusResponse);
    },

    getCityStatus: function(ajaxParams) {
        drawMap.makeTileInView(ajaxParams.col, ajaxParams.row);
        server.sendAsyncAjax('ajax/GetCityStatus', { city: ajaxParams.city }, client.onStatusResponse);
    },

    /** Unit's common actions */

    moveUnitAction: function(ajaxParams) {
        if (!drawMap.isShowLocationsToMove) {
            drawMap.toggleLocationsToMove(ajaxParams.locations);
            return;
        }

        drawMap.hideLocationsToMove();
        if ((drawMap.selectedCol == ajaxParams.col) && (drawMap.selectedRow == ajaxParams.row)) {
            return;
        }

        server.sendChainOfRequests([
            [ "ajax/MoveUnitActionRequest", { unit: ajaxParams.unit, col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onUpdateWorldResponse ],
            [ "ajax/GetUnitStatus", { unit: ajaxParams.unit }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    unitAttackAction: function(ajaxParams) {
        if (!drawMap.isShowLocationsToAttack) {
            drawMap.toggleLocationsToAttack(ajaxParams.locations);
            return;
        }

        drawMap.hideLocationsToAttack();
        if ((drawMap.selectedCol == ajaxParams.ucol) && (drawMap.selectedRow == ajaxParams.urow)) {
            return;
        }

        server.sendChainOfRequests([
            [ "ajax/AttackActionRequest", { attacker: ajaxParams.attacker, col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onUpdateWorldResponse ],
            [ "ajax/GetUnitStatus", { unit: ajaxParams.attacker }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    captureUnitAction: function(ajaxParams) {
        if (!drawMap.isShowLocationsToCapture) {
            drawMap.toggleLocationsToCapture(ajaxParams.locations);
            return;
        }

        drawMap.hideLocationsToCapture();
        if ((drawMap.selectedCol == ajaxParams.ucol) && (drawMap.selectedRow == ajaxParams.urow)) {
            return;
        }

        server.sendChainOfRequests([
            [ "ajax/CaptureUnitActionRequest", { attacker: ajaxParams.attacker, col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onUpdateWorldResponse ],
            [ "ajax/GetUnitStatus", { unit: ajaxParams.attacker }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    destroyUnitAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/DestroyUnitActionRequest", { unit: ajaxParams.unit }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    /** Settlers Actions */

    buildCityAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuildCityActionRequest", { settlers: ajaxParams.settlers }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    /** Workers Actions */

    buildFarmAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuildFarmActionRequest", { workers: ajaxParams.workers }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    removeForestAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/RemoveForestActionRequest", { workers: ajaxParams.workers }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    removeHillAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/RemoveHillActionRequest", { workers: ajaxParams.workers }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    /** City Actions */

    cityAttackAction: function(ajaxParams) {
        if (!drawMap.isShowLocationsToAttack) {
            drawMap.toggleLocationsToAttack(ajaxParams.locations);
            return;
        }

        drawMap.hideLocationsToAttack();
        if ((drawMap.selectedCol == ajaxParams.ucol) && (drawMap.selectedRow == ajaxParams.urow)) {
            return;
        }

        server.sendChainOfRequests([
            [ "ajax/AttackActionRequest", { attacker: ajaxParams.attacker, col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onUpdateWorldResponse ],
            [ "ajax/GetCityStatus", { city: ajaxParams.attacker }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    buildUnitAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuildUnitActionRequest", { city: ajaxParams.city, unitUuid: ajaxParams.unitUuid }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    buyUnitAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuyUnitActionRequest", { city: ajaxParams.city, unitUuid: ajaxParams.unitUuid }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    buildBuildingAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuildBuildingActionRequest", { city: ajaxParams.city, buildingUuid: ajaxParams.buildingUuid }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    buyBuildingAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/BuyBuildingActionRequest", { city: ajaxParams.city, buildingUuid: ajaxParams.buildingUuid }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    destroyBuildingAction: function(ajaxParams) {
        server.sendChainOfRequests([
            [ "ajax/DestroyBuildingActionRequest", { building: ajaxParams.building }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    /** Civilization Actions */

    nextTurnAction: function(ajaxParams) {
        // clear selections
        drawMap.hideLocationsToMove();
        drawMap.hideLocationsToAttack();
        drawMap.hideLocationsToCapture();

        // clear event's panel
        eventPanel.clear();

        server.sendChainOfRequests([
            [ "ajax/NextTurnActionRequest", { }, client.onUpdateWorldResponse ],
            [ "ajax/GetTileStatus", { col: drawMap.selectedCol, row: drawMap.selectedRow }, client.onStatusResponse ],
            [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
        ]);
    },

    declareWarAction: function(ajaxParams) {
        if (confirm(ajaxParams.confirmationMessage)) {
            server.sendChainOfRequests([
                [ "ajax/DeclareWarActionRequest", { otherCivilization: ajaxParams.otherCivilization }, client.onEmptyResponse ],
                [ "ajax/GetCivilizations", { }, client.onStatusResponse ],
                [ "ajax/GetControlPanel", { }, client.onControlPanelResponse ]
            ]);
        }
    }
};
