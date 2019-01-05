"use strict";

var statusPanel = {
    root: {},

    init: function(root) {
        this.root = document.getElementById(root);
    },

    show: function(response) {
        statusPanel.root.innerHTML = response;
    }
};
