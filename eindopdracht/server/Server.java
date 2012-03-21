package eindopdracht.server;

import java.util.ArrayList;

import eindopdracht.server.model.Game;
import eindopdracht.server.model.Lobby;
import eindopdracht.server.network.Network;

public class Server {
	private ArrayList<Lobby> lobbies;
	private ArrayList<Game> games;
	private ArrayList<Player> players;
	private Network network;
	private static int defaultPort;
	
	public Server() {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<Game>();
		this.players = new ArrayList<Player>();
		
		this.network = new Network(defaultPort, this);
	}
	
	public Server(int port) {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<Game>();
		this.players = new ArrayList<Player>();
		
		this.network = new Network(port, this);
	}
	
	/**
	 * Adds the player to the server; Should be used if the player
	 * connects. Also adds the player to the lobby.
	 * @param player
	 */
	public void addPlayer(Player player) {
		players.add(player);
		this.getLobby(player.preferredNumberOfPlayers()).addPlayer(player);
	}
	
	/**
	 * Removes the player from the server; Should be used if the
	 * player quits.
	 * @param player
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	/**
	 * Starts a game with the players of the given lobby and deletes
	 * the lobby.
	 * @param lobby
	 */
	public void startGame(Lobby lobby) {
		Game newGame = new Game(lobby.getPlayers());
		newGame.start();
		lobbies.remove(lobby);
	}
	
	/**
	 * Gets the lobby with the given amount of players max.
	 * @param players
	 * @return
	 */
	public Lobby getLobby(int players) {
		for (Lobby lobby:lobbies) {
			if (lobby.maxNumberOfPlayers() == players)
				return lobby;
		}
		Lobby newLobby = new Lobby(players, this);
		lobbies.add(newLobby);
		return newLobby;
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
