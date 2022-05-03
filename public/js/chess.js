let linkCopied = false;

let connected = false;

let gameId;
let playerId;
let color;

let pieces = [];
let drag = false;
let focusedPieceId;
let selectedPieceId;

let highlightedPosition = { x: -1, y: -1 };

let curX;
let curY;

//comunication
let socket;

function connect() {
    socket = new WebSocket("ws://localhost:8080");

    socket.onopen = () => {
        displayButtons();
        connected = true;
    }

    socket.addEventListener('error', function(event) {
        console.log('WebSocket error: ', event);
    });

    socket.onmessage = (e) => {
        handleMessage(JSON.parse(e.data));
        console.log(JSON.stringify(JSON.parse(e.data), null, 2));
    }

    socket.onclose = () => {
        console.log("connection closed");
        displayLoading();
        connect();
        connected = false;
    }
}

function newGame(type) {
    socket.send(JSON.stringify({
        messageType: "new_game",
        gameType: type
    }));
}

function handleMessage(message) {
    switch (message.messageType) {
        case "game_start":
            if (gameId == null) {
                gameId = message.gameId;
                playerId = message.playerId;
                color = message.color;
                if (message.gameType == "friend") {
                    displayLinkInput(message.playerId);
                }
                console.log("game_start");
            }

            break;
        case "move_response":
            if (message.moveStatus == "OK") {
                playSound("move");
                highlightPosition(message.move.fromX, message.move.fromY);
                highlightPosition(message.move.toX, message.move.toY);
                drawPieces();
            } else {
                pieces[message.move.pieceId].posX = message.move.fromX;
                pieces[message.move.pieceId].posY = message.move.fromY;
                drawBoard();
                drawPieces();
            }
            break;
        case "opponent_moved":
            highlightPosition(pieces[message.pieceId].fromX, pieces[message.pieceId].fromY);
            pieces[message.pieceId].posX = message.toX;
            pieces[message.pieceId].posY = message.toY;
            highlightPosition(toX, toY);
            drawBoard();
            drawPieces();

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

//menu
const withFriendButton = document.querySelector("#withFriendButton");
const withComputerButton = document.querySelector("#withComputerButton");
const loading = document.querySelector(".loading_info");
const modal = document.querySelector("#modal-background");

withFriendButton.addEventListener("click", () => {
    history.pushState(null, null, "/game/create/friend/" + gameId);
    newGame("friend");
});

withComputerButton.addEventListener("click", () => {
    newGame("computer");
    gameStart();
});

function gameStart() {
    modal.style.display = "none";
    playSound("game_start");
    startGameClock(myClock, myTimeLeft);
}

function displayLoading() {
    hideButtons();
    modal.style.display = "block";
    loading.style.display = "block";
}

function displayButtons() {
    loading.style.display = "none";
    withFriendButton.style.display = "inline-block";
    withComputerButton.style.display = "inline-block";
}

function hideButtons() {
    withFriendButton.style.display = "none";
    withComputerButton.style.display = "none";
}

function displayLinkInput(link) {

    hideButtons();
    const linkInput = document.createElement("input");
    linkInput.id = "linkInput"
    linkInput.type = "text";
    linkInput.value = "localhost/game/" + gameId;
    linkInput.readOnly = true;

    const heading = document.createElement("h2");
    heading.innerHTML = "Send this link to your friend:";
    let container = document.querySelector("#modal");
    container.appendChild(heading);

    linkInput.addEventListener("click", () => {
        linkInput.select();

        if (!linkCopied) {

            document.execCommand("copy");
            const lable = document.createElement("label");
            lable.innerHTML = "Link copied!";
            lable.id = "copiedInfo";
            container.appendChild(lable);

            console.log("link copied!");
            linkCopied = true;
        }
    });

    container.appendChild(linkInput);

}

//grafics
const canvas = document.querySelector("#board");
const context = canvas.getContext("2d");

const SQ_LEN = canvas.width / 8;

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
    pieces.push({ pieceId: pieceId, pieceImg: pieceImg, posX: x, posY: y, fromX: x, fromY: y, busy: false });
}

function drawPiece(pieceImg, x, y) {
    pieceImg.onload = function() {
        context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);
    }
    context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);
}

function highlightPosition(x, y) {
    if ((x + y) % 2 == 0) {
        context.fillStyle = "rgb(246, 246, 105)";
    } else {
        context.fillStyle = "rgb(186, 202, 43)";
    }
    context.fillRect(x * SQ_LEN, y * SQ_LEN, SQ_LEN, SQ_LEN);
}

function unhighlightPosition(x, y) {
    if ((x + y) % 2 == 0) {
        context.fillStyle = "rgb(238, 238, 210)";
    } else {
        context.fillStyle = "rgb(119, 149, 86)";
    }
    context.fillRect(x * SQ_LEN, y * SQ_LEN, SQ_LEN, SQ_LEN);
}

function markPosibleMoves(posibleMoves) {
    for (let i = 0; i < posibleMoves.length; i++) {
        markPossibleMove(posibleMoves[i]);
    }
}

//TODO
function markPossibleMove(possition) {
    context.fillStyle = "rgba(0, 0, 0, 0.1)";
    context.arc(possition.x * SQ_LEN - (SQ_LEN / 2), possition.y * SQ_LEN - (SQ_LEN / 2), SQ_LEN / 5.5, 0, 2 * Math.PI);
    context.fill();
}


function update() {

    if (drag) {
        drawBoard();
        highlightPosition(pieces[focusedPieceId].fromX, pieces[focusedPieceId].fromY);
        highlightPosition(pieces[focusedPieceId].posX, pieces[focusedPieceId].posY);
        drawPieces();
        drawPiece(pieces[focusedPieceId].pieceImg, curX - SQ_LEN / 2, curY - SQ_LEN / 2);
        requestAnimationFrame(update);
    }

}

//sounds
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

//controls
canvas.addEventListener("mousedown", function(event) {
    for (id in pieces) {
        if (event.offsetX >= pieces[id].posX * SQ_LEN &&
            event.offsetX <= pieces[id].posX * SQ_LEN + SQ_LEN &&
            event.offsetY >= pieces[id].posY * SQ_LEN &&
            event.offsetY <= pieces[id].posY * SQ_LEN + SQ_LEN
        ) {
            if (pieces[id].pieceId.startsWith(color.charAt(0))) {
                drag = true;
                focusedPieceId = id;
                selectedPieceId = id;
                pieces[id].busy = true;
                document.body.style.cursor = "grabbing"
                break;
            }
        }
    }
    update();
});

canvas.addEventListener("mousemove", function(event) {

    if(drag) {
        document.body.style.cursor = "grabbing"
    } else {
        document.body.style.cursor = "default"

        for (id in pieces) {
            if (event.offsetX >= pieces[id].posX * SQ_LEN &&
                event.offsetX <= pieces[id].posX * SQ_LEN + SQ_LEN &&
                event.offsetY >= pieces[id].posY * SQ_LEN &&
                event.offsetY <= pieces[id].posY * SQ_LEN + SQ_LEN
            ) {
                if (pieces[id].pieceId.startsWith(color.charAt(0))) {
                    document.body.style.cursor = "pointer";
                    break;
                } else {
    
                }
            }
        }
    }

    curX = event.offsetX;
    curY = event.offsetY;
})

canvas.addEventListener("mouseup", function(event) {
    if (drag) {
        document.body.style.cursor = "pointer"
        pieces[focusedPieceId].posX = Math.round((curX - SQ_LEN / 2) / SQ_LEN);
        pieces[focusedPieceId].posY = Math.round((curY - SQ_LEN / 2) / SQ_LEN);
        pieces[focusedPieceId].busy = false;
        drag = false;

        drawBoard();
        highlightPosition(pieces[focusedPieceId].fromX, pieces[focusedPieceId].fromY);
        drawPieces();


        //if piece is moved
        if (pieces[focusedPieceId].posX != pieces[focusedPieceId].fromX || pieces[focusedPieceId].posY != pieces[focusedPieceId].fromY) {

            socket.send(JSON.stringify({
                messageType: "move",
                gameId: gameId,
                playerId: playerId,
                move: {
                    pieceId: focusedPieceId,
                    fromX: pieces[focusedPieceId].fromX,
                    fromY: pieces[focusedPieceId].fromY,
                    toX: pieces[focusedPieceId].posX,
                    toY: pieces[focusedPieceId].posY
                }
            }));

            pieces[focusedPieceId].fromX = pieces[focusedPieceId].posX;
            pieces[focusedPieceId].fromY = pieces[focusedPieceId].posY;

        } else {
            if (pieces[focusedPieceId].posX == highlightedPosition.x && pieces[focusedPieceId].posY == highlightedPosition.y) {
                unhighlightPosition(highlightedPosition.x, highlightedPosition.y);
                highlightedPosition.x = -1;
                highlightedPosition.y = -1;

                drawPiece(pieces[focusedPieceId].pieceImg, pieces[focusedPieceId].posX * SQ_LEN, pieces[focusedPieceId].posY * SQ_LEN);
            } else {
                highlightedPosition.x = pieces[focusedPieceId].posX;
                highlightedPosition.y = pieces[focusedPieceId].posY;
            }
        }
    }
});


//chess clock
let myClock = document.querySelector(".my-clock");
let enemyClock = document.querySelector(".enemy-clock");

//10 minutes
let gameLength = 10 * 60;

let myTimeLeft = gameLength;
let enemyTimeLeft = gameLength;

displayTime(myClock, myTimeLeft);
displayTime(enemyClock, enemyTimeLeft);

function startGameClock(clock, timeLeft) {
    setInterval(() => {
        timeLeft--;
        if(timeLeft == 0) {
            socket.send(JSON.stringify({
                messageType: "time_up",
                gameId: gameId,
                playerId: playerId
            }));
        }
        displayTime(clock, timeLeft);
    }, 1000);
}

function displayTime(clock, time) { 
    let minutes = Math.floor(time / 60);
    let seconds = time % 60;
    seconds = pad(seconds, "0", 2);
    minutes = pad(minutes, "&nbsp;&nbsp;", 2);

    clock.innerHTML = minutes + ":" + seconds;
}

function pad(num, char, size) {
    num = num.toString();
    while (num.length < size) num = char + num;
    return num;
}

connect();
drawBoard();
putPiecesOnBoard();
loadSounds();