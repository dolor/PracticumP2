package eindopdracht.ai;
import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.*;

public abstract class AI {

	int color;
	Board board;
	ArrayList<Integer> otherplayers;
	private String[] chatLines = {""};
	
	/**
	 * 
	 * @param color the color of the AIPlayer
	 * @param board the board of the game
	 * @param players all the players in the game (sorted)
	 * @require color > 0 && color <= 4
	 * 			board != null
	 * 			players.size() > 1
	 * @ensure 	this.getColor = color
	 * 			this.getBoard() = board
	 */
	public AI(int color, Board board, ArrayList<Integer> players)
	{
		this.color = color;
		this.board = board;
		this.otherplayers = players;
		this.otherplayers.remove(Integer.valueOf(color));
	}
	
	/**
	 * @ensure getColor() > 0 && getColor() <= 4
	 * @return the color of the AI player.
	 */
	public int getColor()
	{
		return this.color;
	}
	/**
	 * @ensure getBoard() != null
	 * @return the board
	 */
	public Board getBoard()
	{
		return this.board;
	}
	/**
	 * @ensure getOtherPlayers.size() > 0
	 * @return the other players in the game.
	 */
	public ArrayList<Integer> getOtherPlayers()
	{
		return this.otherplayers;
	}
	
	/**
	 * Calculates the best turn according to the AI
	 * @param turn the turn which has to be filled in
	 */
	public abstract void calculateTurn(Turn turn);

	/**
	 * Calculates the best set according to the AI
	 * @param turn the set which has to be filled in
	 */
	public abstract void calculateSet(Set set);

	/**
	 * Returns a random chatline for this AI
	 * @ensure null if not implemented in AI
	 * @return
	 */
	public String chat() {
		if (chatLines.length > 0)
			return chatLines[(int)Math.random()*chatLines.length];
		else
			return null;
	}
}
