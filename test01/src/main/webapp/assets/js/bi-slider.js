var lsBG = new Array(
    'url(assets/imgs/1.jpg)'
    , 'url(assets/imgs/2.jpg)'
    , 'url(assets/imgs/3.jpg)'
    , 'url(assets/imgs/4.jpg)'
    , 'url(assets/imgs/5.jpg)'
    , 'url(assets/imgs/6.jpg)'
    , 'url(assets/imgs/7.jpg)'
    , 'url(assets/imgs/8.jpg)'
);

function getRandomArbitrary(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}

var k = getRandomArbitrary(0, lsBG.length);
if (k >= lsBG.length) {
    k = lsBG.length - 2;
}
if (k < 0) {
    k = 0;
}
$(document).ready(function () {
    setTimeout(function () {
        $(".z-bg-img").css("background-image", lsBG[k]);
    }, 300);
});