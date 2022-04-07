let canvas = document.querySelector("#board");
let context = canvas.getContext("2d");

const SQ_LEN = canvas.width / 8;

let pieces = [];
let drag = false;
let focusedPieceId;

let curX;
let curY;

let firstMove = true;


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

function drawPieces() {
    pieces.forEach(piece => {
        if (piece.busy == false) {
            drawPiece(piece.pieceImg, piece.posX * SQ_LEN, piece.posY * SQ_LEN);
        }
    });
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
    pieces.push({ pieceId: pieceId, pieceImg: pieceImg, posX: x, posY: y, busy: false });
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
            drag = true;
            focusedPieceId = id;
            pieces[id].busy = true;
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
    pieces[focusedPieceId].posX = Math.round((curX - SQ_LEN / 2) / SQ_LEN);
    pieces[focusedPieceId].posY = Math.round((curY - SQ_LEN / 2) / SQ_LEN);
    pieces[focusedPieceId].busy = false;
    drawBoard();
    drawPieces();
    drag = false;
    if (firstMove) {
        playSound("game_start");
        firstMove = false;
    } else {
        playSound("move");
    }

});

function update() {
    if (drag) {
        drawBoard();
        drawPieces();
        drawPiece(pieces[focusedPieceId].pieceImg, curX - SQ_LEN / 2, curY - SQ_LEN / 2);

        requestAnimationFrame(update);
    }
}

function socketCreate() {
    let socket = new WebSocket("ws://localhost:80");

    socket.addEventListener('error', function(event) {
        console.log('WebSocket error: ', event);
    });

    socket.onopen = () => {
        socket.send("Hello world!");
    }

    socket.onmessage = (e) => {
        console.log("message from server", e.data);
    }

    // setTimeout(() => {
    //     socket.close();
    //     console.log("Socket closed!")
    // }, 3000);
}

function handleMessage(message) {
    switch (message.type) {
        case "game_start":
            playSound("game_start");
            break;
        case "opponent_moved":
            highlightPosition(pieces[message.pieceId].posX, pieces[message.pieceId].posY);
            pieces[message.pieceId].posX = message.x;
            pieces[message.pieceId].posY = message.y;
            highlightPosition(x, y);

            if (message.check) {
                playSound("check");
            } else if (message.castling) {
                playSound("castling")
            } else {
                playSound("move");
            }
            break;
        case "game_over":
            playSound("game_over");
            break;
    }
}


//TODO
function highlightPosition(x, y) {

}

function markPosibleMoves(posibleMoves) {
    for (let i = 0; i < posibleMoves.length; i++) {
        markPossibleMove(posibleMoves[i]);
    }
}

//TODO
function markPossibleMove(possition) {

}

let sounds = [];

function loadSounds() {
    sounds["game_start"] = new Audio("data/audio/game_start.mp3");
    sounds["move"] = new Audio("data/audio/move.mp3");
    sounds["castling"] = new Audio("data/audio/castling.mp3");
    sounds["check"] = new Audio("data/audio/check.mp3");
    sounds["game_over"] = new Audio("data/audio/game_over.mp3");
}

function playSound(type) {
    sounds[type].play();
}

drawBoard();
putPiecesOnBoard();
loadSounds();

socketCreate();