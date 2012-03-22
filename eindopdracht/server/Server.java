package eindopdracht.server;

import java.util.ArrayList;

import eindopdracht.server.model.ServerGame;
import eindopdracht.server.model.Lobby;
import eindopdracht.server.network.Network;

public class Server {
	private ArrayList<Lobby> lobbies;
	private ArrayList<ServerGame> games;
	private ArrayList<ServerPlayer> players;
	private Network network;
	private static int defaultPort = 8888;
	
	public Server() {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<ServerGame>();
		this.players = new ArrayList<ServerPlayer>();
		
		this.network = new Network(defaultPort, this);
	}
	
	public Server(int port) {
		this.lobbies = new ArrayList<Lobby>();
		this.games = new ArrayList<ServerGame>();
		this.players = new ArrayList<ServerPlayer>();
		
		this.network = new Network(port, this);
	}
	
	/**
	 * Adds the player to the server; Should be used if the player
	 * connects. Also adds the player to the lobby.
	 * @param player
	 */
	public void addPlayer(ServerPlayer player) {
		players.add(player);
		this.getLobby(player.preferredNumberOfPlayers()).addPlayer(player);
	}
	
	/**
	 * Removes the player from the server; Should be used if the
	 * player quits.
	 * @param player
	 */
	public void removePlayer(ServerPlayer player) {
		for (Lobby l:lobbies) {
			if (l.containsPlayer(player))
				l.removePlayer(player);
		}
		players.remove(player);
	}
	
	/**
	 * Starts a game with the players of the given lobby and deletes
	 * the lobby.
	 * @param lobby
	 */
	public void startGame(Lobby lobby) {
		ServerGame newGame = new ServerGame(lobby.getPlayers());
		newGame.start();
		lobbies.remove(lobby);
	}
	
	/**
	 * Gets the lobby with the given amount of players max.
	 * @param players
	 * @return
	 */
	public Lobby getLobby(int players) {
		System.out.println("Getting lobby " + players);
		for (Lobby lobby:lobbies) {
			if (lobby.maxNumberOfPlayers() == players) {
				System.out.println("Found, returning!");
				return lobby;
			}
		}
		System.out.println("No lobby found, creating a new one");
		Lobby newLobby = new Lobby(players, this);
		lobbies.add(newLobby);
		System.out.println("Returning!");
		return newLobby;
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
