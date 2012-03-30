package eindopdracht.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eindopdracht.server.network.Network;
import eindopdracht.util.PTLog;

public class ServerController {
	private ArrayList<Lobby> lobbies;
	private ArrayList<ServerGameController> games;
	private ArrayList<ServerPlayer> players;
	private Network network;
	private static int defaultPort = 8888;

	public static int endDueToWinner = 1;
	public static int endDueToRemise = 2;
	public static int endDueToCheat = 3;
	public static int endDueToDisconnect = 4;
	
	/**
	 * Creates a server with port 8888
	 * @ensure exits with an exception if 8888 can not be opened
	 */
	public ServerController() {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<ServerGameController>();
		this.players = new ArrayList<ServerPlayer>();
		
		try {
			this.network = new Network(defaultPort, this);
		} catch (IOException e) {
			PTLog.log("Server", "Failed, port already taken! " + e.getMessage());
		}
	}
	
	/**
	 * Creates a server
	 * @param port the port this server should be listening on
	 * @ensure starts listening on the given port if valid
	 * @ensure asks for a different value if invalid
	 */
	public ServerController(int port) {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<ServerGameController>();
		this.players = new ArrayList<ServerPlayer>();
		
		while (this.network == null) {
			try {
				this.network = new Network(port, this);
			} catch (IOException e) {
				PTLog.log("Server", "Failed, port already taken! " + e.getMessage());
				try {
					port = Integer.parseInt(readString("port > "));
				} catch (NumberFormatException f) {
					PTLog.log("Server", "Come on, just enter a fucking NUMBER!");
				}
			}
		}
	}
	
	/**
	 * Adds the player to the server; Should be used if the player
	 * connects. Also adds the player to the lobby.
	 * @param player
	 */
	public void addPlayer(ServerPlayer player) {
		players.add(players.size(), player);
		this.getLobby(player.preferredNumberOfPlayers()).addPlayer(player);
	}
	
	/**
	 * Removes the player from the server; Should be used if the
	 * player quits.
	 * @param player
	 */
	public void removePlayer(ServerPlayer player) {
		PTLog.log("Server", "Attempting to remove player " + player.getName() + " from the server");
		for (Lobby l:lobbies) {
			if (l.containsPlayer(player)) {
				PTLog.log("Server", "Removing player " + player.getName() + " from a lobby");
				l.removePlayer(player);
			}
		}
		for (ServerGameController g:games) {
			if (g.containsPlayer(player)) {
				PTLog.log("Server", "Removing player " + player.getName() + " from a game, ending game");
				g.endGame(player, endDueToDisconnect);
			}
		}
		players.remove(player);
	}
	
	/**
	 * Starts a game with the players of the given lobby and deletes
	 * the lobby.
	 * @param lobby
	 */
	public void startGame(Lobby lobby) {
		ServerGameController newGame = new ServerGameController(lobby.getPlayers(), this);
		newGame.start();
		lobbies.remove(lobby);
		games.add(newGame);
	}
	
	/**
	 * Stops the given game
	 * @param game
	 */
	public void stopGame(ServerGameController game) {
		games.remove(game);
	}
	
	/**
	 * Gets the lobby with the given amount of players max.
	 * @param players
	 * @return
	 */
	public Lobby getLobby(int players) {
		PTLog.log("Server", "placing player in lobby with " + players + " players");
		for (Lobby lobby:lobbies) {
			if (lobby.maxNumberOfPlayers() == players) {
				return lobby;
			}
		}
		PTLog.log("Server", "Creating a new lobby with " + players + " players");
		Lobby newLobby = new Lobby(players, this);
		lobbies.add(newLobby);
		return newLobby;
	}
	
	/** Leest een regel tekst van standaardinvoer. */
    public String readString(String tekst) {
        System.out.print(tekst);
        String antw = null;
        try {
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(System.in));            
            antw = in.readLine();
        } catch (IOException e) {}

        return (antw == null) ? "" : antw;
    }
}
