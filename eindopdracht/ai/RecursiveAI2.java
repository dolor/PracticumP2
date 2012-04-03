package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.model.Color;
import eindopdracht.model.Position;

public class RecursiveAI2 extends AI {

	ArrayList<Integer> players;
	public RecursiveAI2(int color, Board board, ArrayList<Integer> players) {
		super(color, board, players);
		
		this.players = players;
		// TODO Auto-generated constructor stub
	}

	public static final int WINNEND = 1;
	public static final int VERLIEZEND = -1;
	public static final int ONBESLIST = 0;
	
	// instellingen voor AI
	public static final int RECURSION_DEPTH = 5;
	
	/**
	 * Geeft WINNEND als ik win en VERLIEZEND als ik verlies
	 * @param b het speelboard
	 * @param color de kleur van de zet die gedaan wordt
	 * @return WINNEND / VERLIEZEN / ONBESLIST
	 */
	public int geefUitkomst(Board b, int color)
	{
		int score;
		
		if (b.GetWinners().contains(color))
		{
			score = this.WINNEND;
		}
		else if (b.GetWinners().size() > 0)
		{
			score = this.VERLIEZEND;
		}
		else
		{
			score = this.ONBESLIST;
		}
		
		if (color != this.getColor())
		{
			score = 1 - score;
		}
		
		return score;
	}
	

	@Override
	public void calculateTurn(Turn turn) {
		// TODO Auto-generated method stub
		RandomAI r = new RandomAI(this.getColor(), this.getBoard(), this.players);
		r.calculateTurn(turn);		
	}
	
	/**
	 * Determines which color would be player after the given color
	 * 
	 * @param color
	 *            current player
	 * @return next player
	 */
	public int nextPlayerForColor(int color) {
		for (int player : this.players) {
			if (player == color) {
				if (players.indexOf(player) == players.size() - 1) {
					// was the last player
					return players.get(0);
				} else {
					return players.get(players.indexOf(player) + 1);
				}
			}
		}
		return players.get(0);
	}

	
	public PositionAI getBestMove(Board b, int color, int depth)
	{
		PositionAI returnPos = new PositionAI(-1, -1);
		returnPos.setDepth(-1);

		for (int y = 0; y <= 8; y++)
		{
			for (int x = 8; x >= 0; x--)
			{
				if (b.getTileXY(x, y).getColor() == Color.EMPTY) // valkje = leeg
				{
					// kijk of de zet winnen is
					PositionAI p = new PositionAI(x, y);

					// plaats de zet
					b.set(p.getBlock(), p.getTile(), color);
					
					// haal de uitkomst van de zet op					
					int uitkomst = geefUitkomst(b, this.getColor()) ;

					if (uitkomst == this.WINNEND)
					{
						// als een zet winnend is, meteen returnen
						return p;
					}
					else if (uitkomst == this.ONBESLIST && depth >= 1)
					{

						PositionAI recPos = getBestMove(b, nextPlayerForColor(color), depth-1);
						
						if (returnPos.getDepth() > recPos.getDepth())
						{
							returnPos = recPos;
						}
						
					}
					
					// maak de zet weer leeg;
					b.set(p.getBlock(), p.getTile(), Color.EMPTY);

				}
			}
		}
		return returnPos;
		
	}
	
	@Override
	public void calculateSet(Set set) {
		
		Position pos = this.getBestMove(this.getBoard(), this.getColor(), RECURSION_DEPTH);
		
		set.setBlock(pos.getBlock());
		set.setTile(pos.getTile());
	}

}
