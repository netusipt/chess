# Chess

The application was built as a semestral work in curses Programming in Java and Programming in JavaScript. Players can play against a computer or another player online.
All the chess logic is built into the Java server. JavaScript is used to render the board and handle all user interactions. The chess server is able to host multiple games at once.

# User interface description

On the home page, a user can choose if he wants to play with a computer or fried online. If he chooses the option "with a friend", a link will be generated. He/she has to send it to the opponent. When the opponent arrives at the site, the game is on. There are two ways to move pieces. One of them is a basic drag and drop, and another one is selecting from possible moves.

# Client-server communication

Clients communicate with the server in real-time using a web socket. They send JSON requests to the server. We can see exactly how communication looks like in this diagram.

![API_Diagram](https://user-images.githubusercontent.com/71562948/169720560-64577df9-752a-4d1d-87e9-579e59f4b206.png)

At first, a player sends "NewGameRequest" to the server. The server will generate a game link and send it back to the client in "NewGameResponse". When the opponent arrives at the side, his/her computer will send "ConnectRequest" to the server. At that moment, the server will send "StartGameRequest" to both players. There will be information about player color, which is chosen randomly, and the initial positions of the player's pieces. Every time someone makes a move, the client will send move info to the server in "MoveRequest". The server will answer with "MoveResponse". It will tell the client if a move is valid or not. The server will also send "OpponentMovedRequest" to an opponent. The move will display on his/her screen. In case of the check-mate server will notify clients with "GameOverRequest". Communication is over.

Individual server requests are dynamically converted from data objects into JSON format strings by the GSON library. The conversion also works the other way around. If a request comes from a client, the JSON string is converted to a DTO.

# Architecture

## Backend

I have created classes of individual pieces. Their parent is an abstract class Piece. Pieces have properties like the direction in which they can move. Direction is defined by vector class. I was inspirited by linear algebra. Every piece has a set of vectors. For example, a rook can move horizontally and vertically, so it contains vectors (1, 0) and (0, 1). Bishop contains vectors (1, 1) and (-1, 1). From that vectors computer will compute all linear combinations to get possible moves of a piece. To get correct results we have to generate linear combinations on one vector at a time and then merge the sets.
A game is represented by a Game class. Every game has two players and a referee. The referee decides if a move is possible or not based on the rules. Every Rule class implements a Rule interface. Referee in his method "isMovePermitted" cycles through rules and finds out whether any of them would not be violated by a given move.
GamesManager manages ongoing games. It contains a map of GameControllers. Each game has its ID, under which its controller is stored on the map.

![chess_diagram](https://user-images.githubusercontent.com/71562948/169719885-62791400-da65-4526-9034-857979aa5b55.png)

## Frontend

### Rendering

I use HTML canvas to render the chess board, drawing white and green squares with JavaScript. I have calculated the width of one square from the width of the canvas by just dividing it by eight. Then I loaded pictures of individual pieces and drew them on a chess board.

### Animation

I have set event listeners "mousedown", "mousemove" and "mouseup" on canvas. After a mouse click, the "update" function is called, which re-renders the board and pieces. It calls itself until the mouse is up. Update function enables drag and drop.

![Screenshot_6](https://user-images.githubusercontent.com/71562948/169719816-8700b950-437e-4467-bb5a-56b2d455dbe4.png)
