"use strict";

var scrollMap = {
    canvas: {},

    // For map's panning
    isMouseLeftButtonPressed: false,
    startMouseX: 0,
    startMouseY: 0,

    // Map's shift on key press
    keyOffX: 0,
    keyOffY: 0,

    init: function(canvas) {
        this.canvas = canvas;

        canvas.onmousedown = function(e) { scrollMap.onMouseDown.call(scrollMap, e); };
        canvas.onmouseup = function(e) { scrollMap.onMouseUp.call(scrollMap, e); };
        canvas.onmouseout = function(e) { scrollMap.onMouseOut.call(scrollMap, e); };
        canvas.onmousemove = function(e) { scrollMap.onMouseMove.call(scrollMap, e); };
        canvas.addEventListener('touchstart', function(e) { scrollMap.onTouchStart.call(scrollMap, e); e.preventDefault(); }, false);
        canvas.addEventListener('touchmove', function(e) { scrollMap.onTouchMove.call(scrollMap, e); e.preventDefault(); }, false);
        canvas.addEventListener('touchend', function(e) { scrollMap.onTouchEnd.call(scrollMap, e); e.preventDefault(); }, false);

        document.onkeydown = function(e) { scrollMap.onKeyDown.call(scrollMap, e); };
    },

    onKeyDown: function(e) {
        var dx = 0, dy = 0;
        if (e.keyCode === 37) dx = this.keyOffX;  // Left arrow
        if (e.keyCode === 39) dx = -this.keyOffX; // Right arrow
        if (e.keyCode === 38) dy = this.keyOffY;  // Up arrow
        if (e.keyCode === 40) dy = -this.keyOffY; // Down arrow
        if (e.keyCode === 27) { dx = mapWindow.x; dy = mapWindow.y }; // Esc

        mapWindow.moveLeftUpXY(dx, dy);
        drawMap.redraw();
    },

    getMouseButtonNo: function(e) {
        if (!e.which && e.button) {
            if (e.button & 1) e.which = 1      // Left
            else if (e.button & 4) e.which = 2 // Middle
            else if (e.button & 2) e.which = 3 // Right
        }
        return e.which;
    },

    onMouseDown: function(e) {
        if (this.getMouseButtonNo(e) !== 1) return;
        this.isMouseLeftButtonPressed = true;

        var offset = this.getOffset(e);
        this.startMouseX = offset.x;
        this.startMouseY = offset.y;
        this.isMoved = false;
    },

    onMouseUp: function(e) {
        if (this.getMouseButtonNo(e) !== 1) return;
        this.isMouseLeftButtonPressed = false;

        // now map's moving, just a mouse click
        if (!this.isMoved) {
            this.onMouseClick(e);
        }
    },

    onMouseOut: function(e) {
        this.isMouseLeftButtonPressed = false;
    },

    onMouseMove: function(e) {
        if (!this.isMouseLeftButtonPressed) return;

        var offset = this.getOffset(e);
        mapWindow.moveLeftUpXY(offset.x - this.startMouseX, offset.y - this.startMouseY);
        this.startMouseX = offset.x;
        this.startMouseY = offset.y;
        this.isMoved = true;

        drawMap.redraw();
    },

    onTouchStart: function(e) {
        this.startMouseX = e.changedTouches[0].pageX;
        this.startMouseY = e.changedTouches[0].pageY;
        this.isMoved = false;
    },

    onTouchMove: function(e) {
        mapWindow.moveLeftUpXY(e.changedTouches[0].pageX - this.startMouseX, e.changedTouches[0].pageY - this.startMouseY);
        this.startMouseX = e.changedTouches[0].pageX;
        this.startMouseY = e.changedTouches[0].pageY;
        this.isMoved = true;

        drawMap.redraw();
    },

    onTouchEnd: function(e) {
        if (!this.isMoved) {
            var offx = e.changedTouches[0].pageX - utils.getCumulativeOffset(this.canvas).x;
            var offy = e.changedTouches[0].pageY - utils.getCumulativeOffset(this.canvas).y;
            drawMap.onSelectTile(offx, offy);
        }
    },

    onMouseClick: function(e) {
        var offset = this.getOffset(e);
        drawMap.onSelectTile(offset.x, offset.y);
    },

    getOffset: function(e) {
        var x, y;

        // workaround for Firefox
        if (e.offsetX == undefined) {
            var pl = 0;
            var pt = 0;
            if (e.rangeParent) {
               var obj = e.rangeParent;
               do {
                   pl += obj.offsetLeft;
                   pt += obj.offsetTop;
               } while (obj = obj.offsetParent);
            }
            x = e.layerX - pl; //e.pageX - this.canvas.offsetLeft;
            y = e.layerY - pt; //e.pageY - this.canvas.offsetTop;
        } else {
            x = e.offsetX;
            y = e.offsetY;
        }
        return { "x": x, "y": y };
    }
}