package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.ai.intelligent.IntelligentSet;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;

public class IntelligentAI extends AI {

	public IntelligentAI(int color, Board board) {
		super(color, board);
		
		// TODO Auto-generated constructor stub
	}
	
	// zetten voor beide
	// aantal > 1
	public static final int INSTANT_WIN = 1000000; // Instant win, over 9000
	
	// Scores voor zetten	
	public static final int CENTER_CENTER = 50; // center van het speelveld
	public static final int CENTER_OTHER = 25; // center van een ander block
	public static final int CHAIN_SAME_BLOCK = 10; // reeks op hetzelfde block * aantal (uiteraard max 3)
	public static final int CHAIN_DIAGONAL = 15; // reeks diagonaal * aantal
	public static final int CHAIN_HORIZONTAL = 5; // reeks horizontaal * aantal 
	public static final int CHAIN_VERTICAL = 5; // reeks verticaal * aantal
	public static final int SAME_BLOCK = 10; // aantal ballen op hetzelfde block * aantal

	// voor turns
	public static final int BREAK_OPPONENT_CHAIN = 5; // breekt een reeks van de tegenstander door * aantal
	public static final int CREATE_CHAIN = 7; // creeer een keten voor jezelf * aantal
	
	/* Eerste instantie:
	 * Prioriteit voor zetten:
	 * 		1 Instant Wins
	 * 		2 Midden - Midden
	 *		3 Middens van andere blocks
	 * 		4 Stukken in 1 Block op een rij:
	 * 			1 Diagonalen
	 * 			2 Kruizen (horizontaal en vertical)
	 * 			3 Rest
	 * 		5 
	 * Prioriteit voor draaien:
	 * 		1 Instant wins
	 * 		2 Lange rijen van de tegenstander doorbreken, niet lange rijen van ons (score voor bouwen)
	 * 		3 Lange rijen van onszelf maken
	 * 
	 * Als er tijd over is:
	 * Recursief
	 * 
	 * (non-Javadoc)
	 * @see eindopdracht.ai.AI#calculateTurn(eindopdracht.client.model.Turn)
	 */
	
	@Override
	public void calculateTurn(Turn turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateSet(Set set) {
		// lijst van alle zetten
		ArrayList<IntelligentSet> options = new ArrayList<IntelligentSet>();
		
		for (int x = 0; x <= 8; x++)
		{
			for (int y = 0; y <= 8; y++)
			{
				if (board.getTileXY(x, y).getColor() == 0)
				{
					// de zet is legaal
					
					// maak een intelligent zet met positie aan
					Position pos = new Position(x,y);
					IntelligentSet s = new IntelligentSet(pos);
					
					options.add(s);
					
					// Maak een board om aan te rekenen
					Board newBoard = board.deepCopy();
					newBoard.set(pos.getBlock(), pos.getTile(), this.getColor());
					
					
					// INSTANT_WIN
					
					if (board.GetWinners().contains(this.getColor()))
					{
						s.addScore(INSTANT_WIN);
					}					
					
					// CENTER_CENTER
					if (x == 4 && y == 4)
					{
						s.addScore(CENTER_CENTER);
					}
					// CENTER_OTHER
					else if (pos.getTile() == 4)
					{
						s.addScore(CENTER_OTHER);
					}
					
					// CHAIN_DIAGONAL
					
					
				}
			}
		}
		
		// neem de hoogste en return die
		IntelligentSet pick = options.get(0);
		for (IntelligentSet s : options)
		{
			if (s.getScore() > pick.getScore())
			{
				pick = s;
			}
		}
		
		// stel pick in in set
		set.setBlock(pick.getPosition().getBlock());
		set.setTile(pick.getPosition().getTile());
	}

}
