package project_protocol;

/**
 * Project Module 2 2018-2019: 'Spectrangle'
 * Interface Protocol
 * @author  Vincent van Engers
 * @version 1.0.0
 *
 *
 */


public interface Protocol {

    /**
     * The server stands in between all clients. The clients do not interact with each other but only with the server.
     * All server and clients have a copy of the game. The server has the ‘master version’. It is the responsibility
     * of the server to send updates of what happens in the ‘master version’ to the players.
     *
     * Information will be sent in the form of string messages.
     * Every message consists of multiple attributes. Every attribute is separated by a “,”.
     * The first attribute of every message is the identifier. The identifier signifies what kind of message it is.
     * A player always has 90 seconds to respond to a move-request from the server. If they surpass these 90 seconds
     * the server will skip their turn. If the player tries to make invalid moves, such as placing tiles in locations that do not exist, 
     * their turn is skipped.
     * Messages that are in a wrong format or should not have been sent are answered
     * with an error message as defined below.
     *
     * The client should connect on port 666.
     *
     *
     *
     * WATCH OUT: SEQUENCE OF PARAMETERS OF FUNCTIONS IS EXPLICIT. FOLLOW SEQUENCE AS INDICATED IN JAVADOC.
     * WATCH OUT: ALL PARAMETERS ARE OF THE TYPE "STRING" AND SEPARATED BY A ","
     * WATCH OUT: PARAMETERS MAY NOT INCLUDE " " AND "\"
     * WATCH OUT:Coordinates are used in the same as described in the kick of session of the project.
     *
     */


    /**
     * The protocol
     * The protocol begins after a connection with the server has been established.
     * 1.	The client sends a connect-request to the server.
     * 2.	When the player has been added to the game and the game has started,
     *      then the server sends a game-started to the player.
     * 3.	The server uses a random number generator to choose who may start.
     * 4.	The server sends a move-request to the player whose turn it is.
     * 5.	The player can either sent a move message, a tile-replace or a skip-move.
     * 6.	 Server response:
     *  a.	Case move message: the server checks if the move is valid. If the move is not valid than the server
     *      will skip the players move and sent a skip-move. If the move is valid than the server will send a
     *      tile-representation (of a tile from the bag) to the player and will send a
     *      player-tiles message (of the player who’s turn it was) to all players.
     *  b.	Case tile-replace: the server checks if the player really cannot do any more moves.
     *      If the player can actually make a move with the tiles the player has, the server will skip
     *      the players move and sent a skip-move.
     *      If the player truly cannot make any more moves, the server will remove one tile from
     *      the bag and send a tile-replace to the player and send a player-tiles message of the player who’s turn
     *      it was to all players.
     *   c.	Case skip-move: the server will skip the players move.
     * 7.
     *   a.	Case game over: the server will send a game-over message to all players, close the game and
     *      disconnects from all players.
     *   b.	Case game not over: go to step 4.
     */

    /**
     * Colours will be represented in the following way
     */
    String RED = "R";
    String BLUE = "B";
    String GREEN = "G";
    String YELLOW = "Y";
    String PURPLE = "P";
    String WHITE = "W";


    /**
     * connect-request
     * IS used to connect to the server.
     *
     * List of arguments
     * - Name of client
     *
     * Example:
     * Barry wants to joint the server:
     * "CONNECTREQUEST,Barry"
     *
     */
    String CONNECTREQUEST = "CONNECTREQUEST";


    /**
     * Tile-representation
     * Is used to represent a tile
     *
     * List of arguments:
     * -Colour of left side
     * -Colour of top/bottom side
     * -Colour of right side
     * -Score of tile                               /\
     *                                             / 6\
     * Example: We want to represent the triangle /R  B\
     * "TILE,R,B,G,6"                            /  G   \
     *                                           --------
     *                                           --------
     * Example: We want to represent the triangle\   5  /
     *                                            \Y  P/
     *  "TILE,Y,W,P,5"                             \W /
     *                                              \/
     */
    String TILE = "TILE";

    /**
     * player-tiles
     * Is used to represent all tiles in a players inventory
     *
     * List of arguemnts:
     * -first tile
     * -second tile
     * -third tile
     * -fourth tile
     * -name of player
     *
     * Example:
     * Barry have the tiles (Red, Blue, Green, 6) (Purple, White, Green, 5) (Red, Blue, Yellow, 6) (Red, Blue, Green, 1)
     * "PLAYERTILES,TILE,R,B,G,6,TILE,P,W,G,5,TILE,R,B,Y,6,TILE,R,B,G,1,Barry"
     *
     */
    String PLAYERTILES = "PLAYERTILES";

    /**
     * game-started
     * Is used to communicate that the game has started, which players are in the game and what their tiles are.
     *
     * List of arguments
     * - player-tiles of client1
     * - player-tiles of client2
     * - player-tiles of client3 (optional)
     * - player-tiles of client4 (optional)
     *
     * Example:
     * the game has started with players Barry, Jack and Mary.
     * They all have the following four tiles:
     * (Red, Blue, Green, 6) (Purple, White, Green, 5) (Red, Blue, Yellow, 6) (Red, Blue, Green, 1)
     * "GAMESTARTED,PLAYERTILES,TILE,R,B,G,6,TILE,P,W,G,5,TILE,R,B,Y,6,TILE,R,B,G,1,Barry,PLAYERTILES,TILE,R,B,G,6,TILE,P,W,G,5,TILE,R,B,Y,6,TILE,R,B,G,1,Jack,PLAYERTILES,TILE,R,B,G,6,TILE,P,W,G,5,TILE,R,B,Y,6,TILE,R,B,G,1,Mary"
     *
     */
    String GAMESTARTED = "GAMESTARTED";

    /**
     * move
     * Is used to denote the move of a particular tile to a location
     *
     * List of arguments:
     * -tile-representation
     * -rotation
     * -row
     * -column
     *
     * Requirements for agruments:
     * -Rotation can be 0, 1 or 2 and denotes the clockwise rotation of the tile/colours.
     *
     * Example:
     * We want to move the tile (Green, Red, Blue, 6), twice rotated at location (0,3)
     * "MOVE,TILE,G,R,B,6,2,0,3"
     *
     */
    String MOVE = "MOVE";

    /**
     * move-request
     * Is used to inform a player that it is their turn and that they should send a move.
     *
     */
    String MOVEREQUEST = "MOVEREQUEST";

    /**
     * tile-replace
     * Is used to tell the server that the player wants to replace a tile.
     *
     * List of arguments:
     * -tile-representation.
     *
     * Example:
     * Barry wants to replace his tile (Red, Green Blue, 6)
     * "TILEREPLACE,TILE,R,G,B,6"
     *
     */
    String TILEREPLACE = "TILEREPLACE";

    /**
     * skip
     * Is used for a skip request or inform a player that their turn is being skipped.
     *
     * Example:
     * "SKIP"
     *
     */
    String SKIP = "SKIP";

    /**
     * game-over
     * Is used to inform the players that the game is over and what the points are. Who the winner is, is implied
     * by the points.
     *
     * List of arguments:
     * -name of player 1
     * -score of player 1
     * -name of player 2
     * -score of player 2
     * -name of player 3 (optional)
     * -score of player 3 (optional)
     * -name of player 4 (optional)
     * -score of player 4 (optional)
     *
     * Example:
     * The game is over and Barry has 40 points, Mary has 30 points and Jack has 20 points.
     * "GAMEOVER,Barry,40,Mary,30,Jack,20"
     *
     *
     */
    String GAMEOVER = "GAMEOVER";

    /**
     * error
     * Is used to inform a player that something has gone wrong.
     *
     * List of arguments:
     * -error message
     *
     * Example:
     * A player has send an invalid message
     * "ERROR,You have send and invalid message."
     * 
     * A player has sent a tile they do not own
     * "ERROR, You do not own this tile."
     *
     */
    String ERROR = "ERROR";
    
    /**
     * Makes sure that the tile send by the player is actually owned by the player.
     */
     boolean checkValidTile(Tile tile);
    


    /* ---------------------------EXTRA Functionality ---------------------------	 */

    /**
     * chat-message-client
     * Is used by clients to sent a message to the server.
     *
     * List of arguments:
     * - message
     *
     * Example:
     * Barry wants to sent "Howdy" to all other players.
     * "CLIENTMESSAGE,Howdy"
     */
    String CLIENTMESSAGE = "CLIENTMESSAGE";

    /**
     * chat-message-server
     * Is used by server to sent a message to all clients.
     *
     * List of arguments:
     * - player name
     * - message
     *
     * Example:
     * Barry has sent "Howdy" to the server.
     * "CLIENTMESSAGE,Barry,Howdy"
     */
    String SERVERMESSAGE = "SERVERMESSAGE";

    /**
     * get-client-names
     * Is used by a client to request the names of all connected clients that have this particular extension.
     *
     * Example:
     * "GETCLIENTNAMES"
     */
    String GETCLIENTNAMES = "GETCLIENTNAMES";

    /**
     * Is used by the server to all client names.
     *
     * List of arguments:
     *  - name 1
     *  - name 2
     *  - etc,
     *
     * Example:
     * Players Barry, Jack, Mary, Billy, Joe, Ryan are connected
     * "CLIENTNAMES,Barry,Jack,Mary,Billy,Joe,Ryan"
     *
     */
    String CLIENTNAMES = "CLIENTNAMES";

    /**
     * request-challenge
     * Is ussd by the client to request a challenge.
     *
     * List of arguments:
     * - name 1
     * - name 2 (optional)
     * - name 3 (optional)
     *
     * Example:
     * Barry wants to challange, Jack and Mary.
     * "CHALLANGEPLAYERS,Jack,Mary"
     */
    String CHALLANGEPLAYERS = "CHALLANGEPLAYERS";

    /**
     * sent-challenge
     * Is used by the server to notify a client that they are being challenged.
     *
     * List of arguments:
     * - name of challenger
     *
     * Example:
     * Mary has been challenged by Barry
     * "NOTIFYCHALLENGE,Barry"
     *
     */
    String NOTIFYCHALLENGE = "NOTIFYCHALLENGE";

    /**
     * challenge response
     * This is used by the client being challenged to refuse. They have 60 seconds to refuse the challenge.
     *
     * Example:
     * "ACCEPTCHALLENGE"
     *
     */
    String ACCEPTCHALLENGE = "ACCEPTCHALLENGE";

    /**
     * request leaderboard
     * Is used by the client to request all leaderboards.
     * "REQUESTLEADERBOARD"
     *
     */
    String REQUESTLEADERBOARD = "REQUESTLEADERBOARD";

    /**
     * sent leaderboard
     * Is used to sent the leaderboards to a client
     *
     * List of arguments:
     *  For every player the following three arguments sould be added. Each player is separated by a "\".
     * - name of player who did a move
     * - score associated with the move
     * - time = point of time at which the score has been received in seconds since 1 January 1970 (System.currentTimeMillis())
     *
     * Requirements for agruments:
     * - Score >= 0
     *
     * Example:
     * Bary and Jack have scored 10 and 20 points on 1 January 1970 respectively.
     * "SENDLEADERBOARD,Bary,10,0,Jack,20,0"
     *
     */
    String SENDLEADERBOARD = "SENDLEADERBOARD";

}
