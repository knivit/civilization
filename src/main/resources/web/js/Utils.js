"use strict";

var utils = {
    /**
     * Create a multidimensional array
     * Example:
     * var arr = Utils.createArray(10, 5);
     * Creates arr[0..9][0..4]
    */
    createArray: function(length) {
        var a = new Array(length || 0);

        if (arguments.length > 1) {
            var args = Array.prototype.slice.call(arguments, 1);
            for (var i = 0; i < length; i++) {
                a[i] = utils.createArray.apply(this, args);
            }
        } else {
            for (var i = 0; i < length; i ++) {
                a[i] = null;
            }
        }

        return a;
    },

    sign: function(x) {
        return x ? x < 0 ? -1 : 1 : 0;
    },

    getCumulativeOffset: function(obj) {
        var left, top;
        left = top = 0;
        if (obj.offsetParent) {
            do {
                left += obj.offsetLeft;
                top  += obj.offsetTop;
            } while (obj = obj.offsetParent);
        }

        return { "x": left, "y": top };
    },

    getRandomInt: function(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
}