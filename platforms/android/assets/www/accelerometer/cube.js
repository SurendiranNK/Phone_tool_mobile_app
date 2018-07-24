var orientation = 0;
var vx = 0, vy = 0;
var px = 0, py = 0;
var lastx, lasty;
var tiltx = 0, tilty = 0;
            
function init() {
    window.addEventListener("orientationChanged", function(event) {
        orientation = event.orientation;
    }, true);
    
    window.addEventListener("deviceorientation", function(event) {
        // process event.alpha, event.beta and event.gamma
        if(orientation == 0)    tiltx = event.beta;
        else                    tiltx = (orientation == 90) ? event.gamma : event.gamma * -1;
        if(orientation == 0)    tilty = event.gamma;
        else                    tilty = (orientation == 90) ? event.beta : event.beta * -1;
    }, true);

    /*document.addEventListener('touchstart', function(event) {
        event.preventDefault();
        var touch = event.touches[0];
        lastx = touch.pageX;
        lasty = touch.pageY;
    }, false);

    document.addEventListener('touchmove', function(event) {
        event.preventDefault();
        var touch = event.touches[0];
        var mousex = touch.pageX;
        var mousey = touch.pageY;
        if(lastx !== mousex) vx = mousex - lastx;
        if(lasty !== mousey) vy = mousey - lasty;
        lastx = mousex;
        lasty = mousey;
    }, false);*/
}


function resize(event) {
    //var y = ($(window).height() - 240) * 0.5;
    //$('.cube').css('margin-top', y+'px');
}

$(window).on('resize', resize);
$(document).ready(function() {
    resize();
    init();
});

function render() {
    px += vx;
    py += vy;
    vx *= 0.9;
    vy *= 0.9;
    $('.cube')[0].style.webkitTransform = "rotateX("+(py - tiltx)+"deg) rotateY("+(px - tilty)+"deg)";
}

setInterval(render, 50);