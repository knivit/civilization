"use strict";

var menuPanel = {
    root: {},

    init: function(root) {
        this.root = document.getElementById(root);
    },

    show: function(response) {
        menuPanel.root.innerHTML = response;
    }
};
