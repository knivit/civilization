"use strict";

var controlPanel = {
    root: {},

    init: function(root) {
        this.root = document.getElementById(root);
    },

    show: function(response) {
        controlPanel.root.innerHTML = response;
    }
};
