package eindopdracht.ai;

import java.util.ArrayList;


import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;
import eindopdracht.model.Color;
import eindopdracht.model.Position;
import eindopdracht.util.PTLog;

/**
 * Dit is de 2e versie van de recursieve AI, en degene die we uiteindelijk hebben gebruikt.
 * @author Mick vd Vegt
 *
 */
public class RecursiveAI2 extends AI {

	ArrayList<Integer> players;
	
	public RecursiveAI2(int color, Board board, ArrayList<Integer> players, int depth) {
		super(color, board, players);
		this.RECURSION_DEPTH = depth;
		this.players = players;
		this.chatLines = new String[] { "Hahaha, your going down mate!", "HAAAAA! GAAAAAAAAY",
						"You blocked me on facebook, and now you're going to die!",
				 "Hah!" , "Not bad!"};
		
	}

	public static final int WINNEND = 1;
	public static final int VERLIEZEND = 0;
	public static final int ONBESLIST = -1;
	
	// instellingen voor AI
	public int RECURSION_DEPTH = 3;
	
	/**
	 * Geeft WINNEND als ik win en VERLIEZEND als ik verlies
	 * @param b het speelboard
	 * @param color de kleur van de zet die gedaan wordt
	 * @return WINNEND / VERLIEZEN / ONBESLIST
	 */
	public int geefUitkomst(Board b, int color)
	{
		int score;
		ArrayList<Integer> winners = b.getWinners();
		if (winners.contains(color))
		{
			score = WINNEND;
		}
		else if ( winners.size() > 0)
		{
			score = VERLIEZEND;
		}
		else
		{
			score = ONBESLIST;
		}
		return score;
	}
	
	/**
	 * Gives the best possible rotation for this current board.
	 * @param b the current board
	 * @param playerColor the current player
	 * @param recursionDepth the current recursion depth
	 * @return the best rotation on this board
	 */
	public Rotation giveBestRotation(Board b, int playerColor)
	{
		Rotation returnRot = null;
		
		for (int block = 0; block <= 8; block++)
		{
			for (int rotation = 1; rotation <= 2; rotation++)
			{
				

				b.turn(block, rotation);
				
				Rotation currentRot = new Rotation(block, rotation);
				
				// haal de uitkomst van de zet op					
				int uitkomst = geefUitkomst(b, playerColor) ;
								
				if (uitkomst == RecursiveAI2.WINNEND)
				{
					// als een zet winnend is, meteen returnen, zet de diepte van de victorie op p
					currentRot.setColor(playerColor);
					
					// draai het blok terug
					b.turn(block, Block.getOtherRotation(rotation));
					
					return currentRot;
					
				}
				else if (uitkomst == RecursiveAI2.ONBESLIST)
				{
					if (returnRot == null)
					{
						returnRot = currentRot;
					}			
				}

				// draai het blok weer terug
				b.turn(block, Block.getOtherRotation(rotation));
			}
		}
		return returnRot;
	}
	
	
	@Override
	public void calculateTurn(Turn turn) {
		Rotation r = giveBestRotation(this.getBoard().deepCopy(), this.getColor());
		
		turn.setBlock(r.getBlock());
		turn.setRotation(r.getRotation());		
	}
	
	/**
	 * Determines which color would be player after the given color
	 * 
	 * @param color of current player
	 * @return color of next player
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

		for (int y = 8; y >= 0; y--)
		{
			for (int x = 0; x <= 8; x++)
			{
				if (b.getTileXY(x, y).getColor() == Color.EMPTY) // vakje = leeg
				{

					PositionAI p = new PositionAI(x, y);
					
					if (recursionDepth == 0)
					{
						p.setDepth(recursionDepth);
						return p;
					}

					
					// plaats de zet
					b.set(p.getBlock(), p.getTile(), playerColor, true);
					
					// haal de uitkomst van de zet op					
					int uitkomst = geefUitkomst(b, playerColor) ;
					
					
					// return deze positie als we aan het winnen zijn
					if (uitkomst == RecursiveAI2.WINNEND)
					{
						// als een zet winnend is, meteen returnen, zet de diepte van de victorie op p
						p.setDepth(recursionDepth);
						p.setColor(playerColor);
						b.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
						return p;
						
					}
					
					
					// Als de recursiediepte de eerste is. Kijk of je een zet kan blokkeren
					if (recursionDepth == RECURSION_DEPTH )
					{
						// kijk of de volgende speler kan winnen
						int nextPlayer = nextPlayerForColor(playerColor);
						b.set(p.getBlock(), p.getTile(), nextPlayer, true);
						if (geefUitkomst(b, nextPlayer) == RecursiveAI2.WINNEND)
						{
							p.setDepth(recursionDepth);
							PTLog.log("RecursiveAI", "Block instant win of opponent, depth "+recursionDepth);
							
							// haal de zet weer weg.
							b.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
							
							// return de blokkerende zet
							return p;
						}
					}
					
					if (uitkomst == RecursiveAI2.ONBESLIST)
					{
						b.set(p.getBlock(), p.getTile(), playerColor, true);
						PositionAI recPos = getBestMove(b, nextPlayerForColor(playerColor), recursionDepth-1);

						if (recPos != null)
						{
							if (returnPos == null  || recPos.getDepth() > returnPos.getDepth() && recPos.getColor() == playerColor)
							{
								p.setDepth(recPos.getDepth());
								returnPos = p;
							}
						}
					}
					
					// maak de zet weer leeg;
					b.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
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
		
	}

}
