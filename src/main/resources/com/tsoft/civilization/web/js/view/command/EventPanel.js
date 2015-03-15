"use strict";

var eventPanel = {
    eventTable: {},

    init: function(root) {
        this.eventTable = document.getElementById(root);
    },

    clear: function() {
        eventPanel.eventTable.innerHTML = "";
    },

    add: function(data) {
        var rowCount = eventPanel.eventTable.rows.length;
        var row = eventPanel.eventTable.insertRow(rowCount);

        var cell = row.insertCell(0);
        cell.innerHTML = data.serverTime + "<br>" + data.description;
    }
};
