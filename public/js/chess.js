let linkCopied = false;
let possiblePositionsDisplayed = false;
let possibleMoves = [];
let positionsPieceId;
let connected = false;

let gameId;
let playerId;
let color = "white";

let pieces = [];
let drag = false;
let focusedPieceId;
let selectedPieceId;
let possiblyCaptured;

let highlightedPosition = { x: -1, y: -1 };

let curX;
let curY;

//comunication
let socket;

function connect() {
    socket = new WebSocket("ws://localhost:8080");

    socket.onopen = () => {
        manageURL()
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
        hideLinkInput();
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
        case "init":
            // if (localStorage.getItem("playerId") != null) {
            playerId = message.playerId;
            localStorage.setItem("playerId", playerId);
            // } else {
            //     if (localStorage.getItem("gameId") != null) {
            //         socket.send(JSON.stringify({
            //             messageType: "get_game",
            //             gameId: localStorage.getItem("gameId"),
            //             playerId: localStorage.getItem("playerId"),
            //         }));
            //     }
            // }
            break;
        case "game_start":
            if (gameId == null) {
                gameId = message.gameId;
                console.log("game_start");
            }
            history.pushState(null, null, "/game/play/" + gameId);
            gameStart();
            manageURL();
            break;
        case "get_game_response":
            color = message.color.toLowerCase();
            history.pushState(null, null, "/game/play/" + gameId);
            drawBoard();
            putPiecesOnBoard(message.board.pieces);
            gameStart();
            break;
        case "create_game_response":
            if (gameId == null) {
                gameId = message.gameId;

                if (message.gameType == "friend") {
                    displayLinkInput(message.playerId);
                }
                console.log("game_created");
                console.log("waiting for opponent...");
            }
            break;
        case "move_response":
            displayTime(myClock, message.whiteTimeLeft);
            displayTime(enemyClock, message.blackTimeLeft);

            if (message.moveStatus == "OK") {
                if (message.move.flags.includes("CAPTURE")) {
                    pieces[possiblyCaptured].captured = true;
                    pieces[possiblyCaptured].posX = -1;
                    pieces[possiblyCaptured].posY = -1;
                    drawBoard();
                    drawPieces();
                    playSound("capture");
                } else {
                    playSound("move");
                }

                drawBoard();
                highlightPosition(message.move.fromX, message.move.fromY);
                highlightPosition(message.move.toX, message.move.toY);

                pieces[message.move.pieceId].fromX = message.move.toX;
                pieces[message.move.pieceId].fromY = message.move.toY;
                pieces[message.move.pieceId].posX = message.move.toX;
                pieces[message.move.pieceId].posY = message.move.toY;

                drawPieces();
            } else {
                pieces[message.move.pieceId].posX = message.move.fromX;
                pieces[message.move.pieceId].posY = message.move.fromY;
                drawBoard();
                drawPieces();
            }

            possiblePositionsDisplayed = false;
            break;
        case "opponent_moved":
            displayTime(myClock, message.whiteTimeLeft);
            displayTime(enemyClock, message.blackTimeLeft);

            console.log("opponent moved");
            console.log(message);


            if (message.move.flags.includes("CAPTURE")) {
                pieces[message.move.pieceId].captured = true;
                pieces[message.move.pieceId].posX = -1;
                pieces[message.move.pieceId].posY = -1;
                playSound("capture");
            } else {
                playSound("move");
            }

            if (message.move.flags.includes("PROMOTE")) {
                pieces[message.move.pieceId].pieceId = color.substring(0, 1) + "q";
                pieces[message.move.pieceId].pieceImg.src = "data/" + pieces[message.move.pieceId].pieceId + ".png";
            }

            drawBoard();
            highlightPosition(message.move.fromX, message.move.fromY);
            highlightPosition(message.move.toX, message.move.toY);

            pieces[message.move.pieceId].fromX = message.move.toX;
            pieces[message.move.pieceId].fromY = message.move.toY;
            pieces[message.move.pieceId].posX = message.move.toX;
            pieces[message.move.pieceId].posY = message.move.toY;

            console.log(pieces);
            drawPieces();
            //drawPiece(pieces[message.move.pieceId].pieceImg, pieces[message.move.pieceId].posX * SQ_LEN, pieces[message.move.pieceId].posY * SQ_LEN);

            // if (message.check) {
            //     playSound("check");
            // } else if (message.castling) {
            //     playSound("castling")
            // } else {
            //     playSound("move");
            // }
            break;
        case "get_all_possible_moves":
            if (!possiblePositionsDisplayed || positionsPieceId != focusedPieceId) {
                drawBoard();
                possibleMoves = message.moves;
                markPosibleMoves(message.moves);
                highlightPosition(pieces[focusedPieceId].fromX, pieces[focusedPieceId].fromY);
                drawPieces();
                possiblePositionsDisplayed = true;
                positionsPieceId = focusedPieceId;
            } else {
                drawBoard();
                drawPieces();
                possiblePositionsDisplayed = false;
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
    history.pushState(null, null, "/game/create/friend");
    manageURL();
    newGame("friend");
});

withComputerButton.addEventListener("click", () => {
    history.pushState(null, null, "/game/create/computer");
    manageURL();
    newGame("computer");
    gameStart();
});

function gameStart() {
    modal.style.display = "none";
    playSound("game_start");
    drawBoard();
    drawPieces();
}

function displayLoading() {
    hideButtons();
    //hideLinkInput();
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

let linkInput;
let heading;

function displayLinkInput(link) {

    hideButtons();
    linkInput = document.createElement("input");
    linkInput.id = "linkInput"
    linkInput.type = "text";
    linkInput.value = "localhost/game/join/" + gameId;
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

function hideLinkInput() {
    linkInput.remove();
    heading.remove();
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
        if (piece.busy == false && piece.captured == false) {
            drawPiece(piece.pieceImg, piece.posX * SQ_LEN, piece.posY * SQ_LEN);
        }
    });
}

function putPiecesOnBoard(piecesInfo) {
    drawBoard();
    pieces = [];
    count = 0;

    if (piecesInfo === undefined) {
        createPiece(count++, "br", 0, 0);
        createPiece(count++, "bn", 1, 0);
        createPiece(count++, "bb", 2, 0);
        createPiece(count++, "bq", 3, 0);
        createPiece(count++, "bk", 4, 0);
        createPiece(count++, "bb", 5, 0);
        createPiece(count++, "bn", 6, 0);
        createPiece(count++, "br", 7, 0);

        for (let i = 0; i < 8; i++) {
            createPiece(count++, "bp", i, 1);
        }

        createPiece(count++, "wr", 0, 7);
        createPiece(count++, "wn", 1, 7);
        createPiece(count++, "wb", 2, 7);
        createPiece(count++, "wq", 3, 7);
        createPiece(count++, "wk", 4, 7);
        createPiece(count++, "wb", 5, 7);
        createPiece(count++, "wn", 6, 7);
        createPiece(count++, "wr", 7, 7);

        for (let i = 0; i < 8; i++) {
            createPiece(count++, "wp", i, 6);
        }
    } else {
        piecesInfo.forEach(piece => {
            createPiece(piece.id, piece.tag, piece.x, piece.y);
        });
    }

    drawPieces();
}

function createPiece(id, tag, x, y) {
    let pieceImg = new Image();
    pieceImg.src = "data/" + tag + ".png";
    pieces[id] = { pieceId: tag, pieceImg: pieceImg, posX: x, posY: y, fromX: x, fromY: y, busy: false, captured: false };
}

function drawPiece(pieceImg, x, y) {
    if (color == "black") {
        y = 7 * SQ_LEN - y;
        x = 7 * SQ_LEN - x;
    }

    pieceImg.onload = function() {
        context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);
    }
    context.drawImage(pieceImg, x, y, SQ_LEN, SQ_LEN);
}

function highlightPosition(x, y) {
    if (color == "black") {
        y = 7 - y;
        x = 7 - x;
    }

    if ((x + y) % 2 == 0) {
        context.fillStyle = "rgb(246, 246, 105)";
    } else {
        context.fillStyle = "rgb(186, 202, 43)";
    }
    context.fillRect(x * SQ_LEN, y * SQ_LEN, SQ_LEN, SQ_LEN);
}

function highlightCapturePostition(x, y) {
    context.fillStyle = "rgba(255, 0, 0, 0.3)";
    context.fillRect(x * SQ_LEN, y * SQ_LEN, SQ_LEN, SQ_LEN);
}

function unhighlightPosition(x, y) {
    if (color == "black") {
        y = 7 - y;
        x = 7 - x;
    }

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


function markPossibleMove(possition) {
    let x = possition.toX;
    let y = possition.toY;

    if (color == "black") {
        x = 7 - x;
        y = 7 - y;
    }

    if (possition.flags.includes("CAPTURE")) {
        highlightCapturePostition(x, y);
    } else {
        context.beginPath();
        context.fillStyle = "rgba(0, 0, 0, 0.1)";
        context.arc((x + 1) * SQ_LEN - (SQ_LEN / 2), (y + 1) * SQ_LEN - (SQ_LEN / 2), SQ_LEN / 5.5, 0, 2 * Math.PI);
        context.fill();
        context.closePath();
    }

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
    sounds["capture"] = new Audio("data/audio/capture.mp3");
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
        let posY = pieces[id].posY;
        let posX = pieces[id].posX;

        if (color == "black") {
            posY = 7 - posY;
            posX = 7 - posX;
        }

        if (event.offsetX >= posX * SQ_LEN &&
            event.offsetX <= posX * SQ_LEN + SQ_LEN &&
            event.offsetY >= posY * SQ_LEN &&
            event.offsetY <= posY * SQ_LEN + SQ_LEN
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

    if (drag) {
        document.body.style.cursor = "grabbing"
    } else {
        document.body.style.cursor = "default"

        for (id in pieces) {
            let posY = pieces[id].posY;
            let posX = pieces[id].posX;

            if (color == "black") {
                posY = 7 - posY;
                posX = 7 - posX;
            }

            if (event.offsetX >= posX * SQ_LEN &&
                event.offsetX <= posX * SQ_LEN + SQ_LEN &&
                event.offsetY >= posY * SQ_LEN &&
                event.offsetY <= posY * SQ_LEN + SQ_LEN
            ) {
                if (pieces[id].pieceId.startsWith(color.charAt(0))) {
                    document.body.style.cursor = "pointer";
                    break;
                } else {

                }
            }
        }

        if (possiblePositionsDisplayed) {
            for (let i = 0; i < possibleMoves.length; i++) {
                let y = possibleMoves[i].toY;
                let x = possibleMoves[i].toX;

                if (color == "black") {
                    y = 7 - y;
                    x = 7 - x;
                }
                let pos = possibleMoves[i];

                if (event.offsetX >= x * SQ_LEN &&
                    event.offsetX <= x * SQ_LEN + SQ_LEN &&
                    event.offsetY >= y * SQ_LEN &&
                    event.offsetY <= y * SQ_LEN + SQ_LEN
                ) {
                    document.body.style.cursor = "pointer";
                    break;
                }
            }
        }
    }

    if (color == "black") {
        curY = 8 * SQ_LEN - event.offsetY;
        curX = 8 * SQ_LEN - event.offsetX;
    } else {
        curY = event.offsetY;
        curX = event.offsetX;
    }
})

canvas.addEventListener("mouseup", function(event) {
    if (drag) {
        document.body.style.cursor = "pointer"

        let id;
        if ((id = getPieceId(Math.round((curX - SQ_LEN / 2) / SQ_LEN), Math.round((curY - SQ_LEN / 2) / SQ_LEN))) != -1) {
            possiblyCaptured = id;
        }

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

        } else {
            socket.send(JSON.stringify({
                messageType: "get_all_moves",
                gameId: gameId,
                playerId: playerId,
                piecePosition: {
                    x: pieces[focusedPieceId].fromX,
                    y: pieces[focusedPieceId].fromY
                }
            }));


            if (pieces[focusedPieceId].posX == highlightedPosition.x && pieces[focusedPieceId].posY == highlightedPosition.y) {
                unhighlightPosition(highlightedPosition.x, highlightedPosition.y);

                highlightedPosition.x = -1;
                highlightedPosition.y = -1;

                drawBoard();
                drawPieces();
                drawPiece(pieces[focusedPieceId].pieceImg, pieces[focusedPieceId].posX * SQ_LEN, pieces[focusedPieceId].posY * SQ_LEN);
            } else {
                highlightedPosition.x = pieces[focusedPieceId].posX;
                highlightedPosition.y = pieces[focusedPieceId].posY;
            }
        }
    }
});

canvas.addEventListener("click", function(event) {
    if (possiblePositionsDisplayed) {
        let id;
        if ((id = getPieceId(Math.round((curX - SQ_LEN / 2) / SQ_LEN), Math.round((curY - SQ_LEN / 2) / SQ_LEN))) != -1) {
            possiblyCaptured = id;
        }

        for (let i = 0; i < possibleMoves.length; i++) {
            let y = possibleMoves[i].toY;
            let x = possibleMoves[i].toX;

            if (color == "black") {
                y = 7 - y;
                x = 7 - x;
            }
            let pos = possibleMoves[i];

            if (event.offsetX >= x * SQ_LEN &&
                event.offsetX <= x * SQ_LEN + SQ_LEN &&
                event.offsetY >= y * SQ_LEN &&
                event.offsetY <= y * SQ_LEN + SQ_LEN) {

                socket.send(JSON.stringify({
                    messageType: "move",
                    gameId: gameId,
                    playerId: playerId,
                    move: {
                        pieceId: focusedPieceId,
                        fromX: pieces[focusedPieceId].fromX,
                        fromY: pieces[focusedPieceId].fromY,
                        toX: pos.toX,
                        toY: pos.toY
                    }
                }));
                break;
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
        if (timeLeft == 0) {
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

//URL

function manageURL() {
    console.log("manageURL");
    let path = window.location.pathname;
    let params = path.split("/");

    switch (params[1]) {
        case "":
            console.log("home");
            modal.style.display = "block";
            displayButtons();
        case "game":
            switch (params[2]) {
                case "join":
                    gameId = params[3];
                    setTimeout(() => {
                        socket.send(JSON.stringify({
                            messageType: "join_game",
                            gameId: params[3],
                            playerId: playerId
                        }));
                    }, 100);
                    drawBoard();
                    break;
                case "create":
                    console.log(playerId);
                    socket.send(JSON.stringify({
                        messageType: "create_game",
                        gameType: params[3],
                        playerId: playerId
                    }));
                    break;
                case "play":
                    socket.send(JSON.stringify({
                        messageType: "get_game",
                        gameId: params[3],
                        playerId: playerId
                    }));
                    console.log("get_game_request");
                    break;
            }
            break;
        default:
            break;
    }
}

addEventListener('popstate', manageURL);

function getPieceId(x, y) {
    for (let i = 0; i < pieces.length; i++) {
        if (pieces[i].posX == x && pieces[i].posY == y) {
            return i;
        }
    }
    return -1;
}

connect();
drawBoard();
//putPiecesOnBoard();
loadSounds();