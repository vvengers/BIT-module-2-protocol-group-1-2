package project_protocol;

/**
 * Project Module 2 2018-2019: 'Spectrangle'
 * Interface Protocol
 * @author  Vincent van Engers
 * @version 1.0.3
 *
 *
 */

/**
 * Changelog
 * 
 * version 1.0.4
 * Removed error codes
 * Added explanation for what to do with errors.
 * 
 * version 1.0.3
 * Added definition for coordinate
 * 
 * version 1.0.2
 * Removed vaildity check method
 * 
 * version 1.0.1
 * The game will now start 60 seconds after the second player has joined.
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
     * the server will skip their turn. If the player tries to make invalid moves, such as placing tiles in locations that 
     * do not exist, their turn is skipped.
     * Messages that are in a wrong format or should not have been sent are answered
     * with an error message as defined below.
     * 
     * The game will start 60 seconds after the second player has joined.
     *
     * The client should connect on port 6666.
     *
     *
     * WATCH OUT: The coordinate denotation in this document is (row, column)
     * WATCH OUT: When a client disconnects from the server, this player will be replace by a dumb AI player.
     * WATCH OUT: SEQUENCE OF PARAMETERS OF FUNCTIONS IS EXPLICIT. FOLLOW SEQUENCE AS INDICATED IN JAVADOC.
     * WATCH OUT: ALL PARAMETERS ARE OF THE TYPE "STRING" AND SEPARATED BY A ","
     * WATCH OUT: PARAMETERS MAY NOT INCLUDE " " AND "\"
     * WATCH OUT: Coordinates are used in the same as described in the kick of session of the project.
     * WATCH OUT: When a tile gets flipped(changes the directions at which it points), 
     * 			  the left and right colour switch.
     * 			  Example:
     * 				   /\
     * 				  / 6\
     * 				 /R  B\
     * 				/  G   \
     * 				--------
     * 					|
     * 					| when flipped
     * 					V
     * 				--------
     * 				\   G  /
     * 				 \B  R/
     * 				  \6 /
     *  			   \/
     * 				
     * WATCH OUT: when a tile can only rotate clockwise. 
     * 		This is what it looks like:
     * 	  			   /\   			/\				/\
     * 				  / 6\			   / 6\			   / 6\
     * 				 /R  B\ 		  /G  R\ 		  /B  G\
     * 				/  G   \		 /  B   \		 /  R   \
     * 				--------		 --------  	 	 --------
     *				Flipped			 Flipped		  Flipped
     *				0 times			 1 time			  2times
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
	
	int TIMEOUT = 90; //seconds
    String DELIMITER = ",";
    int PORT = 6666;

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
     * CONNECTREQUEST
     * Client --> Server
     * Is used to connect to the server.
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
     *  Tile Representation
     * Is used to represent a tile. 
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
     * Example: We want to represent the triangle\   W  /
     *                                            \Y  P/
     *  "TILE,Y,W,P,5"                             \5 /
     *                                              \/
     */
    String TILE = "TILE";

    /**
     * PLAYERTILES
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
     * GAMESTARTED
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
     * MOVE
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
     * SKIP
     * Client --> Server
     * Is used for to tell the server to skip a move
     *
     * Example:
     * "SKIP"
     *
     */
    String SKIP = "SKIP";
    
    /**
     * skip
     * Server --> Client
     * Is used to inform that a player has skipped a move.
     *
     * Argument:
     * - name of the player that skipped the move
     *
     * Example:
     * "MOVESKIPPED Barry"
     */
    String MOVESKIPPED = "MOVESKIPPED";

    /**
     * GAMEOVER
     * Is used to inform the players that the game is over and what the points are. 
     * Who the winner is, is implied by the points.
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
     * ERROR
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
     * This is used by the client being challenged to accept. They have 60 seconds to accept the challenge.
     *
     * Example:
     * "ACCEPTCHALLENGE"
     *
     */
    String ACCEPTCHALLENGE = "ACCEPTCHALLENGE";

    /**
     * request leaderboard
     * Client -> Server
     * Is used by the client to request all leaderboards.
     * 
     * List of arguments:
     * - parameter
     *
     * List of parameters: 
     * - top n scores				syntax:"top[n]"
     * - scores above n				syntax:"above[n]"
     * - scroes below n				syntax:"below[n]"
     * - average score of the day	syntax:"avg"
     * 
     * Example:
     * Barry wants the top 13 scores: "REQUESTLEADERBOARD,top13"
     * Barry wants all scores above 55: "REQUESTLEADERBOARD,above55"
     * Barry wants all scores below 55: "REQUESTLEADERBOARD,below55"
     * Barry wants the average score of the day: "REQUESTLEADERBOARD,avg"
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
