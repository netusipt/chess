let canvas = document.querySelector("#board");
let context = canvas.getContext("2d");

const SQ_LEN = canvas.width / 8;

let pieces = [];
let drag = false;


function drawBoard() {
    for (let i = 0; i < 8; i++) {
        for (let j = 0; j < 8; j++) {
            if ((i + j) % 2 == 0) {
                context.fillStyle = "rgb(238, 238, 210)";
            } else {
                context.fillStyle = "rgb(119, 149, 86)";
            }
            context.fillRect(SQ_LEN * j, SQ_LEN * i, SQ_LEN, SQ_LEN);
        }
    }
}

function putPiecesOnBoard() {
    drawPiece("br", 0, 0);
    drawPiece("bn", 1, 0);
    drawPiece("bb", 2, 0);
    drawPiece("bq", 3, 0);
    drawPiece("bk", 4, 0);
    drawPiece("bb", 5, 0);
    drawPiece("bn", 6, 0);
    drawPiece("br", 7, 0);

    for (let i = 0; i < 8; i++) {
        drawPiece("bp", i, 1);
    }

    drawPiece("wr", 0, 7);
    drawPiece("wn", 1, 7);
    drawPiece("wb", 2, 7);
    drawPiece("wq", 3, 7);
    drawPiece("wk", 4, 7);
    drawPiece("wb", 5, 7);
    drawPiece("wn", 6, 7);
    drawPiece("wr", 7, 7);

    for (let i = 0; i < 8; i++) {
        drawPiece("wp", i, 6);
    }
}

function drawPiece(pieceId, x, y) {
    let pieceImg = new Image();
    pieceImg.src = "data/" + pieceId + ".png";
    pieces[pieceId] = pieceImg;

    console.log(pieceImg);
    pieceImg.onload = function() {
        context.drawImage(pieceImg, x * SQ_LEN, y * SQ_LEN, SQ_LEN, SQ_LEN);
    }

}

canvas.addEventListener("mousedown", function(event) {
    console.log("down");
    console.log(event.offsetX);
    drag = true;
    update();
});

canvas.addEventListener("mouseup", function(event) {
    console.log("up");
    drag = false;
});

pieces.forEach(piece => {
    peice.addEventListerner("click", function(event) {

    });
});

function update() {
    if (drag) {
        requestAnimationFrame(update);
    }
}

drawBoard();
putPiecesOnBoard();

let socket = new WebSocket("ws://172.24.96.1:3030");

socket.addEventListener('error', function (event) {
    console.log('WebSocket error: ', event);
});

socket.onopen = () => {
    socket.send("Hello world!");
}


console.log(pieces);

