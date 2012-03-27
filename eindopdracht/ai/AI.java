package eindopdracht.ai;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.*;

public abstract class AI {

	int color;
	Board board;
	public AI(int color, Board board)
	{
		this.color = color;
		this.board = board;
	}
	public int getColor()
	{
		return this.color;
	}
	public Board getBoard()
	{
		return this.board;
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
