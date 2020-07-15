let canvas = null;
let context = null;
let frameRate = 1000 / 30;
let frame = 0;
let assets = [
    'animation/0.png',
    'animation/1.png',
    'animation/2.png',
    'animation/3.png',
    'animation/4.png',
    'animation/5.png',
    'animation/6.png',
    'animation/7.png',
    'animation/8.png',
    'animation/9.png'
];
let frames = [];

let onImageLoad = () => {
    console.log("Image ... ");
}

let setup = () => {
    canvas = document.getElementById("my_canvas");
    context = canvas.getContext("2d");
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    for (let i = 0; i < assets.length; i++) {
        frames.push(new Image());
        frames[i].onload = onImageLoad;
        frames[i].src = assets[i];
    }

    setInterval(animate, frameRate);
}

let animate = () => {
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.drawImage(frames[frame], 192, 192);
    frame = (frame + 1) % frames.length;
}
setup();