package eindopdracht.client;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import eindopdracht.client.gui.PentagoXLWindow;
import eindopdracht.client.gui.gameboard.BoardPanel;
import eindopdracht.client.model.player.AIPlayer;
import eindopdracht.client.model.player.HumanPlayer;
import eindopdracht.client.model.player.NetworkPlayer;
import eindopdracht.client.model.player.Player;
import eindopdracht.client.network.Network;
import eindopdracht.model.Command;
import eindopdracht.util.Protocol;

public class MainController extends Observable implements Observer {

	private Network network;
	private GameController game;
	private Player localPlayer;

	public MainController() {
		PentagoXLWindow frame = new PentagoXLWindow(this);
		frame.setVisible(true);
		this.addObserver(frame);
	}

	/**
	 * Connect to the given host, on the given port
	 * @param host host
	 * @param port port
	 * @ensure sets up a socket and network handler if succesfull, throws an exception if not.
	 */
	public void connect(String host, int port) {
		System.out.println("Connecting with " + host + " on port " + port);
		network = new Network();
		network.addObserver(this);
		if (network.connect(host, port)) {
			this.setChanged();
			this.notifyObservers(network);
		}
	}

	/**
	 * Try to join a lobby
	 * 
	 * @param name
	 *            preferred name
	 * @param players
	 *            amount of players you want to play with
	 * @param humanPlayer
	 *            start as a player if true, starts an AI if false
	 * @require aiType: 0->random, 1->intelligent, 2-> recursive
	 */
	public void join(String name, int players, boolean humanPlayer, int aiType) {
		System.out.println("Joining as " + name + " in a lobby with " + players
				+ " players max");
		System.out.println("Starting with AI type " + aiType);
		if (humanPlayer)
			localPlayer = new HumanPlayer();
		else
			localPlayer = new AIPlayer(aiType);
		localPlayer.setName(name);

		if (network != null) {
			network.join(name, players);
		}
	}

	/**
	 * Disconnect the network
	 */
	public void disconnect() {
		if (network != null) {
			network.quit();
			this.setChanged();
			this.notifyObservers("disconnect");
		}
	}
	
	/**
	 * Exit the game
	 * @ensure the game will be closed
	 * @ensure if there's a network connection it will be closed gently
	 */
	public void exit() {
		if (network != null)
			network.quit();
		System.out.println("QUITTING");
		System.exit(0);
	}
	
	/**
	 * Request a hint for the local player
	 */
	public void requestHint() {
		((HumanPlayer)game.getLocalPlayer()).requestHint();
	}
	
	/**
	 * Send the chat across the network
	 * @param chat chat to send
	 */
	public void sendChat(String chat) {
		chat = chat.replaceAll("\\n","");
		chat = chat.replaceAll("\\t","");
		network.sendChat(chat);
	}

	@Override
	public void update(Observable o, Object object) {
		if (object.getClass().equals(Command.class)) {
			Command command = (Command) object;

			if (command.getCommand().equals(Protocol.START)) {
				// this.playerList.setText("");
				String[] p = command.getArgs();
				this.startGame(p);
			}

			else if (command.getCommand().equals(Protocol.CONNECTED)) {
				localPlayer.setName(command.getArg(0));
				System.out.println("Joined, now has the name "
						+ command.getArg(0));
				this.setChanged();
				this.notifyObservers(localPlayer);
			}
			
			else if (command.getCommand().equals(Protocol.CHAT_SERVER)){
				//Pass it along
				this.setChanged();
				this.notifyObservers(object);
			}
		} else if (object.getClass().equals(GameController.class)) {
			this.game = ((GameController) object);
		}
		
		else if (object.getClass().equals(String.class)) {
			if (object.equals("disconnect")) {
				this.setChanged();
				this.notifyObservers("disconnect");
			}
		}
	}

	/**
	 * Starts a game
	 * 
	 * @param p
	 *            array of playernames that are in the game
	 */
	public void startGame(String[] p) {
		System.out.println("Starting the game!");
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < p.length; i++) {
			if (p[i].equals(localPlayer.getName())) {
				// was the local player
				System.out.println("Found the local player");
				;
				players.add(localPlayer);
			} else {
				NetworkPlayer newPlayer = new NetworkPlayer();
				System.out.println("Adding a networkplayer");
				network.addNetworkPlayer(newPlayer);
				newPlayer.setName(p[i]);
				players.add(newPlayer);
			}
		}
		this.game = new GameController(players);
		
		game.setLocalPlayer(localPlayer);
		game.addObserver(network);
		
		this.setChanged();
		this.notifyObservers(game);

		//Initialize AIs
		if (game.getLocalPlayer().getClass().equals(HumanPlayer.class)) {
			((HumanPlayer) game.getLocalPlayer()).createHintAI();
		}
		else if (localPlayer.getClass().equals(AIPlayer.class)) {
			((AIPlayer) localPlayer).initializeAI();
		}
		game.start();
	}
}
