
package chess.comunication;

import chess.comunication.dto.BoardInfo;
import chess.comunication.dto.Message;
import chess.comunication.dto.request.client.game.GetAllPossibleMovesRequest;
import chess.comunication.dto.request.client.game.GetGameRequest;
import chess.comunication.dto.request.client.game.MoveRequest;
import chess.comunication.dto.request.client.pregame.ConnectRequest;
import chess.comunication.dto.request.client.pregame.CreateGameRequest;
import chess.comunication.dto.response.server.game.GetAllPossibleMovesResponse;
import chess.game.base.Move;
import chess.game.base.Position;
import chess.game.pieces.Piece;
import chess.comunication.dto.request.server.game.OpponentMovedRequest;
import chess.comunication.dto.response.server.game.GetGameResponse;
import chess.comunication.dto.response.server.game.MoveResponse;
import chess.comunication.dto.response.server.pregame.CreateGameResponse;
import chess.game.Game;
import chess.game.player.Color;
import chess.game.player.Player;
import chess.game.player.impl.ComputerPlayer;
import chess.utils.JsonConverter;
import org.java_websocket.WebSocket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {

    private final JsonConverter jsonConverter;
    private final GamesManager gamesManager;
    private final Logger logger;

    private WebSocket connection;

    public MessageHandler(GamesManager gamesManager, JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        this.gamesManager = gamesManager;
        this.logger = Logger.getLogger(MessageHandler.class.getName());
    }

    public void handle(String message) {
        String messageType = this.getMessageType(message);
        Message response;

        switch (messageType) {
            case "create_game":
                response = this.createGame(message);
                break;
            case "join_game":
                response = this.joinGame(message);
                break;
            case "get_game":
                response = this.getGame(message);
                break;
            case "move":
                response = this.move(message);
                break;
            case "get_all_moves":
                response = this.getAllMoves(message);
                break;
            default:
                response = new Message("none", "none", "invalid");
                break;
        }

        String responseString = this.jsonConverter.toJson(response);

        this.logger.log(Level.INFO, "Response: " + responseString);
        connection.send(responseString);
    }

    private Message createGame(String message) {
        CreateGameRequest createGameRequest = this.jsonConverter.toObject(message, CreateGameRequest.class);
        this.logger.log(Level.INFO, "Create game request: " + this.jsonConverter.toJson(createGameRequest));

        Game game = this.gamesManager.createGame();
        game.setPlayer(createGameRequest.getPlayerId(), Color.WHITE, true);
        game.getPlayer(createGameRequest.getPlayerId()).setConnection(this.connection);

        if(createGameRequest.getGameType().equals("computer")) {
            game.setType("computer");
            this.gamesManager.joinGame(game.getId(), UUID.randomUUID().toString(), false);


            GetGameResponse getGameResponse = new GetGameResponse(
                    game.getId(),
                    createGameRequest.getPlayerId(),
                    game.getPlayer(createGameRequest.getPlayerId()).getColor(),
                    game.getBoardInfo(),
                    Color.WHITE.name()
            );
            this.connection.send(this.jsonConverter.toJson(getGameResponse));
        } else {
            game.setType("human");
        }

        Message response = new CreateGameResponse(game.getId(), createGameRequest.getPlayerId(), game.getPlayer(createGameRequest.getPlayerId()).getColor().name(), createGameRequest.getGameType());

        return response;
    }

    private Message joinGame(String message) {
        ConnectRequest connectRequest = this.jsonConverter.toObject(message, ConnectRequest.class);
        this.logger.log(Level.INFO, "Join game request: " + this.jsonConverter.toJson(connectRequest));

        this.gamesManager.joinGame(connectRequest.getGameId(), connectRequest.getPlayerId(), true);
        Game game = this.gamesManager.getGame(connectRequest.getGameId());
        game.getPlayer(connectRequest.getPlayerId()).setConnection(this.connection);

        String opponentId = this.gamesManager.getGame(connectRequest.getGameId()).getOpponent(connectRequest.getPlayerId()).getId();

        Message response = new GetGameResponse(connectRequest.getGameId(), opponentId, Color.WHITE, game.getBoardInfo(), Color.WHITE.name());
        game.getOpponent(connectRequest.getPlayerId()).getConnection().send(this.jsonConverter.toJson(response));
        response.setPlayerId(connectRequest.getPlayerId());
        ((GetGameResponse) response).setColor(Color.BLACK);


        return response;
    }

    private Message getGame(String message) {
        GetGameRequest getGameRequest = this.jsonConverter.toObject(message, GetGameRequest.class);
        this.logger.log(Level.INFO, "Get game request: " + this.jsonConverter.toJson(getGameRequest));

        Game game = this.gamesManager.getGame(getGameRequest.getGameId());
        Player player = game.getPlayer(getGameRequest.getPlayerId());
        player.setConnection(this.connection);

        BoardInfo boardInfo = game.getBoardInfo();
        Message response = new GetGameResponse(getGameRequest.getGameId(), getGameRequest.getPlayerId(), player.getColor(), boardInfo, game.getTurn().name());

        return response;
    }


    private Message move(String message) {
        MoveRequest moveRequest = this.jsonConverter.toObject(message, MoveRequest.class);
        this.logger.log(Level.INFO, "Move request: " + this.jsonConverter.toJson(moveRequest));

        Game game = this.gamesManager.getGame(moveRequest.getGameId());
        Move move = game.addFlags(game.getPlayer(moveRequest.getPlayerId()).getColor(), moveRequest.getMove());
        boolean movePossible = game.updateBoard(game.getPlayer(moveRequest.getPlayerId()).getColor(), moveRequest.getMove());

        String moveStatus = "";
        if(movePossible) {

            if(game.getType().equals("human")) {
                OpponentMovedRequest opponentResponse = new OpponentMovedRequest(
                        moveRequest.getGameId(),
                        moveRequest.getPlayerId(),
                        move,
                        game.getTimeLeft(Color.WHITE),
                        game.getTimeLeft(Color.BLACK)
                );

                this.logger.log(Level.INFO, "Opponent moved request: " + this.jsonConverter.toJson(opponentResponse));
                game.getOpponent(moveRequest.getPlayerId()).getConnection().send(this.jsonConverter.toJson(opponentResponse));
            } else {
                ComputerPlayer computerPlayer = ((ComputerPlayer)game.getOpponent(moveRequest.getPlayerId()));
                Move computedMove;

                do {
                    Position position = computerPlayer.getChosenPiecePosition(game.getBoard());
                    computerPlayer.setPossibleMoves(game.getPossibleMoves(computerPlayer.getId(), position));
                    computedMove = computerPlayer.move();
                } while (computedMove == null);

                OpponentMovedRequest opponentMovedRequest = new OpponentMovedRequest(
                        moveRequest.getGameId(),
                        computerPlayer.getId(),
                        computedMove,
                        game.getTimeLeft(Color.WHITE),
                        game.getTimeLeft(Color.BLACK)
                );

                game.setTurn(game.getPlayer(moveRequest.getPlayerId()).getColor());
                this.logger.log(Level.INFO, "Computer moved request: " + this.jsonConverter.toJson(opponentMovedRequest));
                game.getPlayer(moveRequest.getPlayerId()).getConnection().send(this.jsonConverter.toJson(opponentMovedRequest));
            }

            moveStatus = "OK";
        } else {
            moveStatus = "IMPOSSIBLE";
        }

        Message response = new MoveResponse(
                moveRequest.getGameId(),
                moveRequest.getPlayerId(),
                move,
                moveStatus,
                game.getTimeLeft(Color.WHITE),
                game.getTimeLeft(Color.BLACK)
        );

        return response;
    }

    private Message getAllMoves(String message) {
        GetAllPossibleMovesRequest getAllMovesRequest = this.jsonConverter.toObject(message, GetAllPossibleMovesRequest.class);
        Game game = this.gamesManager.getGame(getAllMovesRequest.getGameId());
        Position piecePosition = getAllMovesRequest.getPiecePosition();
        Piece piece = game.getBoard().getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();

        Message response = new GetAllPossibleMovesResponse(
                getAllMovesRequest.getGameId(),
                getAllMovesRequest.getPlayerId(),
                game.getPossibleMoves(getAllMovesRequest.getPlayerId(), piecePosition),
                piece.getId()
        );

        return response;
    }

    public void setConnection(WebSocket connection) {
        this.connection = connection;
    }

    public String generateInitMessage() {
        Message init = new Message(UUID.randomUUID().toString(), "init");
        return this.jsonConverter.toJson(init);
    }

    private String getMessageType(String message) {
        Message request = this.jsonConverter.toObject(message, Message.class);
        return request.getMessageType();
    }

}
