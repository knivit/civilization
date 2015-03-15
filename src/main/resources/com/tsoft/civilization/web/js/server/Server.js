"use strict";

var server = {
    onTimeout: function() {
        xmlHttp.abort();
        alert("Time over");
    },

    sendAsyncAjax: function(url, args, callback, owner) {
        var str = "";
        if (args) {
            for (var arg in args) {
                if (str.length > 0) str += "&";
                str += encodeURIComponent(arg) + "=" + encodeURIComponent(args[arg]);
            }
        }

        var debugInfo = "";
        if (config.DEBUG_AJAX) {
            debugInfo = "POST " + url + "(" + str + ")";
        }

        if (!callback) {
            callback = client.onStatusResponse;
        }

        // create a request to the server
        var xmlHttp = new XMLHttpRequest();
        var serverResponse = new ServerResponse(xmlHttp, callback, owner, debugInfo);
        xmlHttp.onreadystatechange = serverResponse.onStateChange;

        try {
            xmlHttp.open("POST", url, true);
            xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xmlHttp.send(str);

            // set timeout 10 sec
       //     this.timeout = setTimeout(this.onTimeout, 10000);

            if (config.DEBUG_AJAX) {
                console.log(debugInfo);
            }
        } catch (e) {
            alert("Can't connect to server:\n" + e.toString());
        }
    },

    // Chain of requests will be executed coherently
    // but this function itself is asynchronous
    sendChainOfRequests: function(requests) {
        var syncRequests = new SyncRequests(requests);
        syncRequests.process();
    }
};
