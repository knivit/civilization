"use strict";

function ServerResponse(xmlHttpRequest, callback, owner, debugInfo) {
    this.xmlHttpRequest = xmlHttpRequest;
    this.callback = callback;
    this.owner = owner;
    this.debugInfo = debugInfo;

    xmlHttpRequest.serverResponse = this;
    this.onStateChange = function() {
        // 'this' is refers to XMLHttpRequest
        if (config.DEBUG_AJAX) {
            console.log(debugInfo + "; xmlHttp.readyState=" + this.readyState);
        }

        if (this.readyState !== 4) return;

        // response is OK, send it to process
        if (this.status === 200) {
            var contentType = this.getResponseHeader("Content-Type");
            if (contentType.indexOf("application/json") > 0) {
            }
            this.serverResponse.callback.call(this.serverResponse.owner, this.responseText);
        } else

        // it's OK, show a popup to the client
        if (this.status === 202) {
            var response = JSON.parse(this.responseText);
            alert(response.message);
        }

        // something goes wrong, show an alert
        else {
            alert(this.responseText);
        }
   };
};