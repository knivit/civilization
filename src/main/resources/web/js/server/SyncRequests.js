"use strict";

function SyncRequests(requests) {
    this.requests = requests;
    this.reqNo = 0;

    this.process = function() {
        if (this.reqNo < this.requests.length) {
            var url = this.requests[this.reqNo][0];
            var args = requests[this.reqNo][1];
            server.sendAsyncAjax(url, args, this.onServerResponse, this);
        }
    };

    this.onServerResponse = function(response) {
        var originalCallback = this.requests[this.reqNo][2];
        originalCallback(response);

        this.reqNo ++;
        this.process();
    }
}