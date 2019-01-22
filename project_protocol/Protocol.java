package project_protocol;

/**
 * Project Module 2 2018-2019: 'Spectrangle'
 * Interface Protocol
 * @author  Vincent van Engers
 * @version 1.0.5
 */

/** 
 * changelog
 * 1.0.5
 * - Corrected example for the replace tile for turnmade
 * 
 * 1.0.4
 * - Changed from using coordinate to index
 * - Major change to starting the game:
 * 	 Instead of the game starting 60 seconds after the second player has joined,
 * 	 players can give a preference when sending JOINGAME.
 *   The server will have multiple queues. One for every preference(2,3,4).
 *   The players preference will be used! And there will not be an automic switch to other queues.
 * 
 * 1.0.3
 * - Changes to TURNMADE 
 * 
 * 1.0.2
 * - Add pipes to gameStarted message
 * 
 * 1.0.1
 * - SKIPMOVE changed to SKIP
 * - Port 6666 instead of 666
 * - Added missing colons
 * - Added illustrations
 * - Added list of valid tiles. These are the tiles everyone must use!
 * - Added not for chat messages
 * - Other smaller mistakes
 */


public interface Protocol {

    /**
     * The server stands in between all clients. The clients do not interact with each other but only with the server.
     * It is the responsibility of the server to send updates of what happens to the players.
     *
     * - Information will be sent in the form of string messages.
     * - Every message consists of multiple attributes. Every attribute is separated by a “,”.
     * - The first attribute of every message is the identifier. This word signifies what kind of message it is.
     * - A player always has 90 seconds to respond to a move-request from the server. If they surpass these 90 seconds
     *   the server will kick the player. If the player tries to make invalid moves, such as placing tiles in locations that
     *   do not exist, they are kicked.
     * - If the client sends an unknown message and the server was not waiting for a response from that client,
     * 		then the server should ignore the client.
     * - The client should connect on port 6666.
     * - The server needs to make sure there are no two clients with the same name.
     * - Players can give a preference when sending JOINGAME.
     *   The server will have multiple queues. One for every preference(2,3,4).
     *   The players preference will be used! And there will not be an automic switch to other queues.
     * 
     * - Whenever a client is kicked the following happens to the game:
     * 		- Case 1 client left after kick: This client wins end game ends.
     * 		- Case 2 or more clients left after kick: Game continues, the kicked clients tiles
     * 			are put back in the bag and a PLAYERKICKED is send to all clients.
     * 
     * - For the chat extension, the server recieves chatmessages from the clients and destributes
     * 	 these messages to the other clients. Clients never directly communicate!
     *
     * WATCH OUT: SEQUENCE OF PARAMETERS OF FUNCTIONS IS EXPLICIT. FOLLOW SEQUENCE AS INDICATED IN JAVADOC.
     * WATCH OUT: ALL PARAMETERS ARE OF THE TYPE "STRING" AND SEPARATED BY A "," (the DELIMITER)
     * WATCH OUT: PARAMETERS MAY NOT INCLUDE spaces (" "), slashes ("\"), pipes ("|") and commas (",")
     * WATCH OUT: Coordinates are used in the same way as described in the kick of session of the project.
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
     *				Rotated			 Rotated		  Rotated
     *				0 times			 1 time			  2times
     *
     *
     *
     * The protocol
     * The protocol begins after a connection with the server has been established.
     * 1.	The client sends a CONNECTREQUEST to the server.
     * 2. 	The server sends a CONNECTACCEPT to the client.
     * 3.	The player will be added to a game when it sends a JOINGAME
     * 4.	When the player has been added to the game and the game has started,
     *      then the server sends a GAMESTARTED to the player.
     * 5.	The server chooses who may start and sends a MOVEREQUEST to this player.
     * 6.	The player can either sent a MOVE message, a TILEREPLACE or a SKIP.
     * 7.   Server response:
     * 		
     * 		The server will send a TURNMADE to all players after any of the following procedures:		
     * 
     *      a.	Case MOVE:
     *          The server checks if the move is valid. If the move is not valid then the server
     *          will kick the player and sent a PLAYERKICKED to all players. If the move is
     *          valid the server will put the tile on the board. When TURNMADE is send, the playertiles
     *          includes a new tile from the bag.
     *      b.	Case TILEREPLACE:
     *          The server checks if the player really cannot do any more moves.
     *          If the player can actually make a move with the tiles the player has, the server will kick
     *          the player and send a PLAYERKICKED to all players
     *          If the player truly cannot make any more moves, the server will remove one tile from
     *          the bag.
     *      c.	Case SKIP:
     *      	If the player can actually make a move with the tiles the player has, the server will kick
     *          the player and send a PLAYERKICKED to all players.
     *          If the player truly cannot make any more moves the server will skip the players move.
     * 8.   Proceeding...
     *      a.	Case GAMEOVER:
     *          The server will send a GAMEOVER message to all players and close the game: go to step 3.
     *      b.	Case game not over: go to step 4.
     */
	
	/**
	 * List of valid tiles:
	 * 
	 * Fist letter is left, second is upper/lower and the third is right.
	 * 
	 * RRR6
	 * BBB6
	 * GGG6
	 * YYY6
	 * PPP6
	 * RRY5
	 * RRP5
	 * BBR5
	 * BBP5
	 * GGR5
	 * GGB5
	 * YYG5
	 * YYB5
	 * PPY5
	 * PPG5
	 * RRB4
	 * RRG4
	 * BBG4
	 * BBY4
	 * GGY4
	 * GGP4
	 * YYR4
	 * YYP4
	 * PPR4
	 * PPB4
	 * YBP3
	 * RGY3
	 * BGP3
	 * GRB3
	 * BRP2
	 * YPR2
	 * YPG2	
	 * GRP1
	 * BYG1
	 * RYB1
	 * WWW1
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
     * Is used to connect to the server and show what kind of extensions the client supports.
     *
     * List of arguments
     * - Name of client
     * - Chatbox: format:"B" (optional)
     * - Challenge: format:"C" (optional)
     * - Leaderboard: format:"L" (optional)
     * 
     * Using one of these optional arguments does mean that the client supports AND wants 
     * to use the extension.
     *
     * Example:
     * Client Barry wants to join the server and has implemented the 
     * chatbox and challenge extension:
     * "CONNECTREQUEST,Barry,BC"
     *
     */
    String CONNECTREQUEST = "CONNECTREQUEST";
    
    /**
     * CONNECTACCEPT 
     * Server --> Client
     * Is used to show that the connection has been accepted and what kind of extensions the server
     * supports.
     * List of arguments
     * - Chatbox: format:"B" (optional)
     * - Challenge: format:"C" (optional)
     * - Leaderboard: format:"L" (optional)
     * 
     * Example:
     * The server accepts the connection and uses the leaderboard:
     * "CONNECTACCEPT,L"
     * 
     */
    String CONNECTACCEPT = "CONNECTACCEPT";
    
    /**
     * PLAYERKICKED
     * Server --> Client
     * is used to show which player is kicked
     *
     * Argument:
     * - name of kicked player
     * 
     */
    String PLAYERKICKED = "PLAYERKICKED";
    
    /**
     * JOINGAME
     * Client --> Server
     * Is used to communicate the clients wants to join a game.
     * 
     * Argument:
     * - playercount preference
     * 
     *  Example:
     *  Barry wants to play a game with two players
     *  "JOINGAME,2"
     */
    String JOINGAME = "JOINGAME";

    /**
     * Tile Representation
     * Is used to represent a tile. Colours are seen from the upwards pointing Tile
     *
     * List of arguments:
     * -Colour of left side
     * -Colour of top/bottom side
     * -Colour of right side
     * -Score of tile                               /\
     *                                             / 6\
     * Example: We want to represent the triangle /R  B\
     * "TILE,RBG6"                               /  G   \
     *                                           --------
     *                                           --------
     * Example: We want to represent the triangle\   5  /
     *                                            \Y  P/
     *  "TILE,PWY5"                                \W /
     *  !! Note the switched P and Y                \/
     *  !! (tile is upside down)
     */
    String TILE = "TILE";

    /**
     * PLAYERTILES
     * Is used to represent all tiles in a players' inventory
     *
     * List of arguemnts:
     * - first tile
     * - second tile
     * - third tile
     * - fourth tile
     * - name of player
     *
     * Example:
     * Barry has the tiles (Red, Blue, Green, 6) (Purple, White, Green, 5) (Red, Blue, Yellow, 6) (Red, Blue, Green, 1)
     * "PLAYERTILES,RBG6,PWG5,RBY6,RBG1,Barry"
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
     * "GAMESTARTED,|RBG6,PWG5,RBY6,RBG1,Barry|RBG6,PWG5,RBY6,RBG1,Jack|RBG6,PWG5,RBY6,RBG1,Mary|"
     *
     */
    String GAMESTARTED = "GAMESTARTED";

    /**
     * MOVE
     * Client --> Server
     * Is used to denote the move of a particular tile to a location
     *
     * List of arguments:
     * -tile-representation
     * -rotation
     * -index
     *
     * Requirements for agruments:
     * -Rotation can be 0, 1 or 2 and denotes the clockwise rotation of the tile/colours.
     *
     * Example:
     * We want to move the tile (Green, Red, Blue, 6), twice rotated at index 3
     * "MOVE,GRB6,2,3"
     *
     */
    String MOVE = "MOVE";
    
    /**
     * Turn done
     * Is used to inform clients that a player has played a turn.
     * Server --> Client
     * 
     * List of arguemnts:
     * - What kind of turn. An actual move [M], tile replacement[R], move was skipped[S]
     * - name
     * - PlayerTiles of the player that made the move
     * - What tile was replaced (only used with [R])
     * - move (only used with [M])
     * 
     * Examples:
     * Barry has made put tile (G, R, B, 6) twice rotated at index 3:
     * "TURNMADE,M,Barry,RBG6,PWG5,RBY6,RBG1,GRB6,2,3"
     * 
     * Barry has replaced his tile (Y, Y, Y, 1) and received tile (G, R, B, 6)
     * "TURNMADE,R,Barry,RBG6,PWG5,RBY6,RBG1,RYYY1,"
     * 
     * Barry has skipped his move.
     * "TURNMADE,S,Barry,RBG6,PWG5,RBY6,RBG1"
     * 
     */
    String TURNMADE = "TURNMADE";

    /**
     * move-request
     * Is used to inform a player that it is their turn and that they should send a move.
     *
     */
    String MOVEREQUEST = "MOVEREQUEST";

    /**
     * TILEREPLACE
     * Client --> Server
     * Is used to tell the server that the player wants to replace a tile.
     *
     * List of arguments:
     * -tile-representation.
     *
     * Example:
     * Barry wants to replace his tile (Red, Green Blue, 6)
     * "TILEREPLACE,RGB6"
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
     * GAMEOVER
     * Is used to inform the players that the game is over and what the points are.
     * Who the winner is, is implied by the points.
     *
     * List of arguments:
     * - name of player 1
     * - score of player 1
     * - name of player 2
     * - score of player 2
     * - name of player 3 (optional)
     * - score of player 3 (optional)
     * - name of player 4 (optional)
     * - score of player 4 (optional)
     *
     * Example:
     * The game is over and Barry has 40 points, Mary has 30 points and Jack has 20 points.
     * "GAMEOVER,Barry,40,Mary,30,Jack,20"
     *
     *
     */
    String GAMEOVER = "GAMEOVER";


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
     * Is used by the client to request a challenge.
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
     * Is used to send the leaderboards to a client
     *
     * List of arguments:
     * For every player the following three arguments should be added.
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
