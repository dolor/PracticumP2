package eindopdracht.client;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import eindopdracht.client.gui.PentagoXLWindow;

import eindopdracht.client.model.player.AIPlayer;
import eindopdracht.client.model.player.HumanPlayer;
import eindopdracht.client.model.player.NetworkPlayer;
import eindopdracht.client.model.player.Player;
import eindopdracht.client.network.Network;
import eindopdracht.model.Command;
import eindopdracht.util.PTLog;
import eindopdracht.util.Protocol;

public class MainController extends Observable implements Observer {

	private Network network;
	private GameController game;
	private Player localPlayer;
	
	//Save these details so we can reconnect
	private String host;
	private int port;
	private String playerName;
	private int lobbySize;
	private boolean humanPlayer;
	private int aiType;
	private int numberOfReplays;
	private int numberOfWins;
	private int aiDepth;

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
		this.host = host;
		this.port = port;
		
		PTLog.log("MainController", "Connecting with " + host + " on port " + port);
		network = new Network();
		network.addObserver(this);
		if (network.connect(host, port)) {
			this.setChanged();
			this.notifyObservers(network);
		}
	}
	
	/**
	 * Sets the number of times this game should automatically restart
	 * @param replays
	 */
	public void setNumberOfReplays(int replays) {
		this.numberOfReplays = replays;
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
	public void join(String name, int players, boolean humanPlayer, int aiType, int aiDepth) {
		playerName = name;
		lobbySize = players;
		this.humanPlayer = humanPlayer;
		this.aiType = aiType;
		this.aiDepth = aiDepth;
		
		PTLog.log("MainController", "Joining as " + name + " in a lobby with " + players
				+ " players max");
		if (!humanPlayer)
			PTLog.log("MainController", "Starting with AI type " + aiType);
		if (humanPlayer)
			localPlayer = new HumanPlayer(aiDepth);
		else
			localPlayer = new AIPlayer(aiType, aiDepth);
		localPlayer.setName(name);

		if (network != null) {
			network.addObserver(localPlayer);
			network.join(name, players);
		}
	}
	
	/**
	 * Restart the last game. Can only be used after a game has been played.
	 */
	public void restart() {
		this.connect(host, port);
		this.join(playerName, lobbySize, humanPlayer, aiType, aiDepth);
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
		PTLog.log("MainController", "QUITTING");
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
		if (chat.split(" ").length > 0 && !chat.equals("") && !chat.equals(" "))
			network.sendChat(chat);
	}

	@Override
	public void update(Observable o, Object object) {
		if (object.getClass().equals(Command.class)) {
			Command command = (Command) object;
			
			if (command.getCommand().equals(Protocol.START)) {
				String[] p = command.getArgs();
				this.startGame(p);
			}

			else if (command.getCommand().equals(Protocol.CONNECTED)) {
				localPlayer.setName(command.getArg(0));
				PTLog.log("MainController", "Joined, now has the name "
						+ command.getArg(0));
				this.setChanged();
				this.notifyObservers(localPlayer);
			}
			
			else if (command.getCommand().equals(Protocol.QUIT_SERVER)) {
				PTLog.log("MainController", "The server quit");
				this.disconnect();
				if (this.numberOfReplays > 0) {
					PTLog.log("MainController", "Going to play " + numberOfReplays + " more games");
					this.numberOfReplays--;
					PTLog.log("MainController", "Already won " + numberOfWins + " Games!");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e){}
					this.restart();
				}
			}
			
			else if (command.getCommand().equals(Protocol.END_GAME)) {
				if (Integer.parseInt(command.getArg(0)) == 1) {
					if (command.getArg(1).equals(this.localPlayer.getName()))
						numberOfWins++;
				}
			}
			
		} else if (object.getClass().equals(GameController.class)) {
			this.game = ((GameController) object);
		}
		
		else if (object.getClass().equals(String.class)) {
			if (object.equals("disconnect")) {
				this.setChanged();
				this.notifyObservers("disconnect");
				game.endGame(GameController.endDueToDisconnect);
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
		PTLog.log("Maincontroller", "Starting the game!");
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < p.length; i++) {
			if (p[i].equals(localPlayer.getName())) {
				// was the local player
				players.add(localPlayer);
				network.setLocalPlayer(localPlayer);
				network.addObserver(localPlayer);
			} else {
				NetworkPlayer newPlayer = new NetworkPlayer();
				network.addNetworkPlayer(newPlayer);
				newPlayer.setName(p[i]);
				players.add(newPlayer);
			}
		}
		this.game = new GameController(players);
		
		game.setLocalPlayer(localPlayer);
		game.addObserver(network);
		game.addObserver(this);
		
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
