var errorCount = 0;

var console = {
    log: function(message) {
        log(message);
    },

    jsUnitLog: function(message, methodName) {
        jsUnitLog(message, methodName);
    }
};

function assertEquals(expected, value, msg) {
    if (expected !== value) {
        console.log("Expected " + expected + ", but got " + value);
        errorCount ++;
    }
};

function test(testObject) {
    errorCount = 0;

    // execute the setup method
    if (testObject.setup !== undefined) {
        testObject.setup();
    }

    // get the test methods
    var testFunctions = [];
    for (var prop in testObject) {
        if ((typeof testObject[prop] === "function") && (prop.substr(0,4) === 'test')) {
            testFunctions.push(prop);
        }
    }

    // do them
    for (var i = 0; i < testFunctions.length; i ++) {
        var errorsBefore = errorCount;
        var testFunction = testFunctions[i];
        console.jsUnitLog("Executing ", testFunction);
        testObject[testFunction]();

        if (errorsBefore !== errorCount) {
            console.log(fun + "FAILED !!!");
        } else {
            console.log(fun + "passed");
        }
    }

    // shutdown
    if (testObject.shutdown !== undefined) {
        testObject.shutdown();
    }

    console.log(errorCount + " error(s) found");
};
