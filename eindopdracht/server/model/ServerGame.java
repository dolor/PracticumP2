package eindopdracht.server.model;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.server.Server;
import eindopdracht.server.model.Set;
import eindopdracht.server.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.util.ModelUtil;

public class ServerGame extends Observable {

	ArrayList<ServerPlayer> players;
	ServerPlayer settingPlayer; // Player die aan de beurt is

	Board board;

	public ServerGame(ArrayList<ServerPlayer> players) {
		this.players = players;
		this.board = new Board();

		for (ServerPlayer player : players) {
			player.setGame(this);
		}
	}

	/**
	 * Start the game
	 */
	public void start() {
		System.out.println("Game started with " + players.size() + " players!");

		// Tell the players that the game is starting
		String msg = "start";
		for (ServerPlayer p : players)
			msg = msg + " " + p.getName();

		this.broadcast(msg);

		// TODO write this code
	}

	/**
	 * Tries to set. If invalid move, returns FALSE and broadcasts to all clients.
	 * @param turn
	 * @return
	 */
	public boolean set(Set set) {
		if (set.getPlayer().getState() == ServerPlayer.IDLE || !(settingPlayer.getState() == ServerPlayer.SETTING)) {
			this.endGame(set.getPlayer(), Server.endDueToCheat);
			return false;
		} else {
			if (!board.set(set.getBlock(), set.getTile(), set.getPlayer()
					.getColor())) {
				this.endGame(set.getPlayer(), Server.endDueToCheat);
				return false;
			} else {
				settingPlayer.setState(ServerPlayer.TURNING);
				this.broadcast("set_tile "
						+ ModelUtil.intToLetter(set.getBlock()) + " "
						+ set.getTile() + " " + set.getPlayer().getName());
				return true;
			}
		}
	}

	/**
	 * Tries to turn. If invalid move, returns FALSE and broadcasts to all clients.
	 * @param turn
	 * @return
	 */
	public boolean turn(Turn turn) {
		if (turn.getPlayer().getState() == ServerPlayer.IDLE || !(settingPlayer.getState() == ServerPlayer.TURNING)) {
			this.endGame(turn.getPlayer(), Server.endDueToCheat);
			return false;
		} else {
			if (!board.turn(turn.getBlock(), turn.getRotation())) {
				this.endGame(turn.getPlayer(), Server.endDueToCheat);
				return false;
			} else {
				settingPlayer.setState(ServerPlayer.IDLE);
				settingPlayer = getNextPlayer(settingPlayer);
				settingPlayer.setState(ServerPlayer.SETTING);
				this.broadcast("turn_block "
						+ ModelUtil.intToLetter(turn.getBlock()) + " "
						+ ModelUtil.intToDirection(turn.getRotation()));
				return true;
			}
		}
	}
	
	/**
	 * Returns the next player in the list
	 * @param currentPlayer
	 * @return
	 */
	public ServerPlayer getNextPlayer(ServerPlayer currentPlayer) {
		ServerPlayer nextPlayer = players.get(0);
		//Loops through all but the last player, setting the next player in the list
		//as the next player. If it doesn't find the current player, the current
		//player was the last in the list.
		for (int i=0; i<players.size() - 1; i++) {
			if(players.get(i).equals(currentPlayer))
				nextPlayer = players.get(i);
		}
		return nextPlayer;
	}

	/**
	 * Quit the server. Tell all players that the game is over, and the reason why.
	 */
	public void endGame(ServerPlayer player, int reason) {
		System.out.println("Ending the game due to reason " + reason);
		this.broadcast("end_game " + player.getName() + " " + reason);
		for (ServerPlayer p:players) {
			players.remove(p);
		}
	}
	
	/**
	 * Checks if this lobby contains the given player
	 * @param player
	 * @return
	 */
	public boolean containsPlayer(ServerPlayer player) {
		for (ServerPlayer p:players) {
			if (p.equals(player))
				return true;
		}
		return false;
	}
	
	public void broadcast(String message) {
		System.out.println("Broadcasting: " + message);
		for (ServerPlayer player : players)
			player.sendMessage(message);
	}
}
