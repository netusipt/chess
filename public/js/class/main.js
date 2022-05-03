let canvas = document.querySelector("#board");
let context = canvas.getContext("2d");
const SQ_LEN = canvas.width / 8;

class Piece {
    posX
    posY
}

class Board {

    static width = 8;
    static height = 8;

    drawBoard() {
        for (let i = 0; i < height; i++) {
            for (let j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    context.fillStyle = "rgb(238, 238, 210)";
                } else {
                    context.fillStyle = "rgb(119, 149, 86)";
                }
                context.fillRect(SQ_LEN * j, SQ_LEN * i, SQ_LEN, SQ_LEN);
            }
        }
    }
}

let board = new Board();





let pieces = [];
let drag = false;
let focusedPieceId;

let curX;
let curY;




function drawPieces() {
    for (let i = 0; i < pieces.length; i++) {
        drawPiece(pieces[i].pieceImg, pieces[i].posX * SQ_LEN, pieces[i].posY * SQ_LEN);
    }
}

function putPiecesOnBoard() {
    createPiece("br", 0, 0);
    createPiece("bn", 1, 0);
    createPiece("bb", 2, 0);
    createPiece("bq", 3, 0);
    createPiece("bk", 4, 0);
    createPiece("bb", 5, 0);
    createPiece("bn", 6, 0);
    createPiece("br", 7, 0);

    for (let i = 0; i < 8; i++) {
        createPiece("bp", i, 1);
    }

    createPiece("wr", 0, 7);
    createPiece("wn", 1, 7);
    createPiece("wb", 2, 7);
    createPiece("wq", 3, 7);
    createPiece("wk", 4, 7);
    createPiece("wb", 5, 7);
    createPiece("wn", 6, 7);
    createPiece("wr", 7, 7);

    for (let i = 0; i < 8; i++) {
        createPiece("wp", i, 6);
    }

    drawPieces();
}

function createPiece(pieceId, x, y) {
    let pieceImg = new Image();
    pieceImg.src = "data/" + pieceId + ".png";
    pieces.push({ pieceId: pieceId, pieceImg: pieceImg, posX: x, posY: y });
}

function drawPiece(pieceImg, x, y) {
    pieceImg.onload = function() {
        context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);
    }
    context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);

}

canvas.addEventListener("mousedown", function(event) {
    for (id in pieces) {
        if (event.offsetX >= pieces[id].posX * SQ_LEN &&
            event.offsetX <= pieces[id].posX * SQ_LEN + SQ_LEN &&
            event.offsetY >= pieces[id].posY * SQ_LEN &&
            event.offsetY <= pieces[id].posY * SQ_LEN + SQ_LEN
        ) {
            console.log("down", pieces[id].pieceId, pieces[id].posX, pieces[id].posY);
            drag = true;
            focusedPieceId = id;
        }
    }
    drag = true;
    update();
});

canvas.addEventListener("mousemove", function(event) {
    curX = event.offsetX;
    curY = event.offsetY;
})

canvas.addEventListener("mouseup", function(event) {
    console.log("up", event.offsetX, event.offsetY);
    pieces[focusedPieceId].posX = Math.round((curX - SQ_LEN / 2) / SQ_LEN);
    pieces[focusedPieceId].posY = Math.round((curY - SQ_LEN / 2) / SQ_LEN);
    drawBoard();
    drawPieces();
    drawPiece(pieces[focusedPieceId].pieceImg, pieces[focusedPieceId].x, pieces[focusedPieceId].y);
    drag = false;
});



function update() {
    if (drag) {
        console.log("fdsfsdf");
        drawBoard();
        drawPieces();
        drawPiece(pieces[focusedPieceId].pieceImg, curX - SQ_LEN / 2, curY - SQ_LEN / 2);

        requestAnimationFrame(update);
    }
}

drawBoard();
putPiecesOnBoard();

let socket = new WebSocket("ws://172.24.96.1:3030");

socket.addEventListener('error', function(event) {
    console.log('WebSocket error: ', event);
});

socket.onopen = () => {
    socket.send("Hello world!");
}


console.log(pieces);