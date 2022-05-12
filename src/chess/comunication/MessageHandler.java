
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
import chess.utils.JsonConverter;
import org.java_websocket.WebSocket;
import java.util.UUID;

public class MessageHandler {

    private final JsonConverter jsonConverter;
    private final GamesManager gamesManager;

    private WebSocket connection;

    public MessageHandler(GamesManager gamesManager, JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        this.gamesManager = gamesManager;
    }

    public String handle(String message) {
        String messageType = this.getMessageType(message);
        Message response;

        switch (messageType) {
            case "create_game":
                CreateGameRequest createGameRequest = this.jsonConverter.toObject(message, CreateGameRequest.class);
                System.out.println("Create game request: " + this.jsonConverter.toJson(createGameRequest));
                Game game = this.gamesManager.createGame();
                System.out.println(createGameRequest.getPlayerId());
                game.setPlayer(createGameRequest.getPlayerId(), Color.WHITE, true);
                game.getPlayer(createGameRequest.getPlayerId()).setConnection(this.connection);
                if(createGameRequest.getGameType().equals("computer")) {
                    this.gamesManager.joinGame(game.getId(), UUID.randomUUID().toString(), false);
                }

                response = new CreateGameResponse(game.getId(), createGameRequest.getPlayerId(), game.getPlayer(createGameRequest.getPlayerId()).getColor().name(), createGameRequest.getGameType());
                System.out.println(response.getMessageType());
                break;
            case "join_game":
                ConnectRequest connectRequest = this.jsonConverter.toObject(message, ConnectRequest.class);
                this.gamesManager.joinGame(connectRequest.getGameId(), connectRequest.getPlayerId(), true);
                Game currentGame = this.gamesManager.getGame(connectRequest.getGameId());
                currentGame.getPlayer(connectRequest.getPlayerId()).setConnection(this.connection);

                String opponentId = this.gamesManager.getGame(connectRequest.getGameId()).getOpponent(connectRequest.getPlayerId()).getId();
                System.out.println(opponentId);


                response = new GetGameResponse(connectRequest.getGameId(), opponentId, Color.WHITE, currentGame.getBoardInfo(), Color.WHITE.name());
                currentGame.getOpponent(connectRequest.getPlayerId()).getConnection().send(this.jsonConverter.toJson(response));
                response.setPlayerId(connectRequest.getPlayerId());
                ((GetGameResponse) response).setColor(Color.BLACK);

                System.out.println("join_game");
                break;
            case "get_game":
                GetGameRequest getGameRequest = this.jsonConverter.toObject(message, GetGameRequest.class);
                Game gameCurr = this.gamesManager.getGame(getGameRequest.getGameId());
                Player player = gameCurr.getPlayer(getGameRequest.getPlayerId());
                player.setConnection(this.connection);

                BoardInfo boardInfo = gameCurr.getBoardInfo();
                response = new GetGameResponse(getGameRequest.getGameId(), getGameRequest.getPlayerId(), player.getColor(), boardInfo, gameCurr.getTurn().name());

                System.out.println("get_game");
                break;
            case "move":
                MoveRequest moveRequest = this.jsonConverter.toObject(message, MoveRequest.class);

                Game gameCurr2 = this.gamesManager.getGame(moveRequest.getGameId());
                System.out.println(gameCurr2.getTurn());
                System.out.println(gameCurr2.getPlayer(moveRequest.getPlayerId()).getColor());
                Move move = gameCurr2.setFlags(gameCurr2.getPlayer(moveRequest.getPlayerId()).getColor(), moveRequest.getMove());
                boolean movePossible = gameCurr2.updateBoard(gameCurr2.getPlayer(moveRequest.getPlayerId()).getColor(), moveRequest.getMove());

                String moveStatus = "";
                if(movePossible) {


                    OpponentMovedRequest opponentResponse = new OpponentMovedRequest(
                            moveRequest.getGameId(),
                            moveRequest.getPlayerId(),
                            move,
                            gameCurr2.getTimeLeft(Color.WHITE),
                            gameCurr2.getTimeLeft(Color.BLACK)
                    );

                    System.out.println(this.jsonConverter.toJson(opponentResponse));
                    gameCurr2.getOpponent(moveRequest.getPlayerId()).getConnection().send(this.jsonConverter.toJson(opponentResponse));

                    moveStatus = "OK";
                } else {
                    moveStatus = "IMPOSSIBLE";
                }

                response = new MoveResponse(
                        moveRequest.getGameId(),
                        moveRequest.getPlayerId(),
                        move,
                        moveStatus,
                        gameCurr2.getTimeLeft(Color.WHITE),
                        gameCurr2.getTimeLeft(Color.BLACK)
                );
                break;
            case "get_all_moves":
                GetAllPossibleMovesRequest getAllMovesRequest = this.jsonConverter.toObject(message, GetAllPossibleMovesRequest.class);
                Game gameCurr3 = this.gamesManager.getGame(getAllMovesRequest.getGameId());
                Position piecePosition = getAllMovesRequest.getPiecePosition();
                Piece piece = gameCurr3.getBoard().getTiles()[piecePosition.getY()][piecePosition.getX()].getPiece();

                response = new GetAllPossibleMovesResponse(
                        getAllMovesRequest.getGameId(),
                        getAllMovesRequest.getPlayerId(),
                        gameCurr3.getPossibleMoves(getAllMovesRequest.getPlayerId(), piecePosition),
                        piece.getId()
                );
                break;
            default:
                response = new Message("none", "none", "invalid");
                break;
        }

        System.out.println("Response: " + this.jsonConverter.toJson(response));
        return this.jsonConverter.toJson(response);
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
