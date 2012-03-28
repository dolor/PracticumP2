package eindopdracht.ai;
import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.*;

public abstract class AI {

	int color;
	Board board;
	ArrayList<Integer> otherplayers;
	public AI(int color, Board board, ArrayList<Integer> players)
	{
		this.color = color;
		this.board = board;
		this.otherplayers = players;
		this.otherplayers.remove(Integer.valueOf(color));
	}
	public int getColor()
	{
		return this.color;
	}
	public Board getBoard()
	{
		return this.board;
	}
	public ArrayList<Integer> getOtherPlayers()
	{
		return this.otherplayers;
	}
	
	/**
	 * Vult de turn in.
	 * @param turn
	 */
	public abstract void calculateTurn(Turn turn);

	/**
	 * Vult de set in.
	 * @param set
	 */
	public abstract void calculateSet(Set set);

}
