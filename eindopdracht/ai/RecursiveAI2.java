package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.model.Color;
import eindopdracht.model.Position;
import eindopdracht.util.PTLog;

public class RecursiveAI2 extends AI {

	ArrayList<Integer> players;
	public RecursiveAI2(int color, Board board, ArrayList<Integer> players) {
		super(color, board, players);
		
		this.players = players;
	}

	public static final int WINNEND = 1;
	public static final int VERLIEZEND = 0;
	public static final int ONBESLIST = -1;
	
	// instellingen voor AI
	public static final int RECURSION_DEPTH = 7;
	
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
		
		/*if (score == this.WINNEND && color != this.getColor())
		{
			score = this.VERLIEZEND;
		}
		else if (score == thi)*/
		
		return score;
	}
	

	@Override
	public void calculateTurn(Turn turn) {
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

	
	public PositionAI getBestMove(Board b, int playerColor, int recursionDepth)
	{
		PositionAI returnPos = null;

		for (int y = 0; y <= 8; y++)
		{
			for (int x = 0; x <= 8; x++)
			{
				if (b.getTileXY(x, y).getColor() == Color.EMPTY) // valkje = leeg
				{
					// kijk of de zet winnen is
					PositionAI p = new PositionAI(x, y);
					

					// plaats de zet
					b.set(p.getBlock(), p.getTile(), playerColor, true);
					
					// haal de uitkomst van de zet op					
					int uitkomst = geefUitkomst(b, playerColor) ;
					
					
					
					if (uitkomst == this.WINNEND)
					{
						// als een zet winnend is, meteen returnen, zet de diepte van de victorie op p
						PTLog.log("RecursiveAI", "Winning move at "+x+","+y);
						p.setDepth(recursionDepth);
						p.setColor(playerColor);
						b.set(p.getBlock(), p.getTile(), Color.EMPTY);
						return p;
						
					}
					// de eerst volgende zet voor de tegestander is bij deze positie winnend
					else if (recursionDepth == RECURSION_DEPTH && geefUitkomst(b, nextPlayerForColor(playerColor)) == this.WINNEND)
					{
						p.setDepth(recursionDepth);
						b.drawBoard();
						PTLog.log("RecursiveAI", "Block instant win of opponent, depth "+recursionDepth);
						b.set(p.getBlock(), p.getTile(), Color.EMPTY);
						return p;
					}
					else if (uitkomst == this.ONBESLIST && recursionDepth >= 1)
					{

						PositionAI recPos = getBestMove(b, nextPlayerForColor(playerColor), recursionDepth-1);

						if (recPos != null)
						{
							if (returnPos == null  || recPos.getDepth() > returnPos.getDepth() && recPos.getColor() == playerColor)
							{
								returnPos = recPos;
							}
						}

						//PTLog.log("RecursiveAI", "recPos: "+recPos.getDepth()+" returnPos: "+returnPos.getDepth());
						
					}
					b.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
					// maak de zet weer leeg;
					

				}
			}
		}
		return returnPos;
		
	}
	
	@Override
	public void calculateSet(Set set) {
		
		Position pos = this.getBestMove(this.getBoard().deepCopy(), this.getColor(), RECURSION_DEPTH);
		
		set.setBlock(pos.getBlock());
		set.setTile(pos.getTile());
		PTLog.log("RecursiveAI", "Move: "+pos.getX()+", "+pos.getY());
	}

}
