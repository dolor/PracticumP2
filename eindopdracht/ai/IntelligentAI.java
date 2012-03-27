package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.ai.intelligent.IntelligentSet;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
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
	public static final int CENTER_CENTER = 200; // center van het speelveld
	public static final int CENTER_OTHER = 100; // center van een ander block
	public static final int CHAIN_SAME_BLOCK = 15; // reeks op hetzelfde block * aantal (uiteraard max 3)
	public static final int CHAIN_DIAGONAL = 10; // reeks diagonaal * aantal
	public static final int CHAIN_HORIZONTAL = 5; // reeks horizontaal * aantal 
	public static final int CHAIN_VERTICAL = 5; // reeks verticaal * aantal
	public static final int SAME_BLOCK = 2; // aantal ballen op hetzelfde block * aantal

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
		RandomAI r = new RandomAI(this.getColor(), this.getBoard());
		r.calculateTurn(turn);
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
					
					// CHAIN_SAME_BLOCK
					int chainSameBlock = 1;
					
					// CHAIN_HORIZONTAL
					int xL = pos.getX()-4; // maximum x aan de linker kant
					if (xL < 0)
						xL = 0;
					int xR = xL + 4; // maximim x aan de rechterkant
					if (xR > 8)
						xR = 8;
					int yI = y;
					int chainLength = 1;
					// kijk links
					for (int xI = x; x >= xL; x--)
					{
						if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
						{
							chainLength++;
							if (new Position(xI, yI).getBlock() == pos.getBlock())
								chainSameBlock++;
						}
						else
						{
							break;
						}
					}
					for (int xI = x; x <= xR; x++)
					{
						if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
							
						{
							chainLength++;
							if (new Position(xI, yI).getBlock() == pos.getBlock())
								chainSameBlock++;
						}
						else
						{
							break;
						}
						
					}
					if (chainLength > 1)
						s.addScore(chainLength * CHAIN_HORIZONTAL);
					
					
					// CHAIN_VERTICAL
					int yL = pos.getY()-4; // maximum x aan de linker kant
					if (yL < 0)
						yL = 0;
					int yR = yL + 4; // maximim x aan de rechterkant
					if (yR > 8)
						yR = 8;
					int xI = x;
					chainLength = 1;
					// kijk links
					for (yI = y; y >= yL; y--)
					{
						if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
						{
							chainLength++;
							if (new Position(xI, yI).getBlock() == pos.getBlock())
								chainSameBlock++;
						}
						else
						{
							break;
						}
					}
					for (yI = y; y <= yR; y++)
					{
						if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
							
						{
							chainLength++;
							if (new Position(xI, yI).getBlock() == pos.getBlock())
								chainSameBlock++;
						}
						else
						{
							break;
						}
						
					}
					if (chainLength > 1)
						s.addScore(chainLength * CHAIN_VERTICAL);
					
					// CHAIN_DIAGONAL
					chainLength = 1;
					// check de borders
					xL = pos.getX()-4;
					if (xL < 0)
						xL = 0;
					xR = xL + 4;
					if (xR > 8)
						xR = 8;
					
					int yU = pos.getY()-4;
					if (yU < 0)
						yU = 0;
					int yD = pos.getY()+4;
					if (yD > 8)
						yD = 8;
					
					// CHECK LEFT_UP
					for (yI = y; yI >= yU; yI--)
					{
						for (xI = x; xI >= xL; xI--)
						{
							if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
							{
								chainLength++;
								if (new Position(xI, yI).getBlock() == pos.getBlock())
									chainSameBlock++;
							}
							else
							{
								break;
							}
						}
					}
					
					// CHECK LEFT_DOWN
					for (yI = y; yI <= yD; yI++)
					{
						for (xI = x; xI >= xL; xI--)
						{
							if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
							{
								chainLength++;
								if (new Position(xI, yI).getBlock() == pos.getBlock())
									chainSameBlock++;
							}
							else
							{
								break;
							}
						}
					}
					
					// CHECK RIGHT_UP
					for (yI = y-1; yI >= yU; yI--)
					{
						for (xI = x; xI <= xR; xI++)
						{
							if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
							{
								chainLength++;
								if (new Position(xI, yI).getBlock() == pos.getBlock())
									chainSameBlock++;
							}
							else
							{
								break;
							}
						}
					}
					
					// CHECK RIGHT_DOWN
					for (yI = y+1; yI <= yD; yI++)
					{
						for (xI = x; xI <= xR; xI++)
						{
							if (newBoard.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
								
							{
								chainLength++;
								if (new Position(xI, yI).getBlock() == pos.getBlock())
									chainSameBlock++;
							}
							else
							{
								break;
							}
						}
					}
					
					if (chainLength > 1)
						s.addScore(chainLength * CHAIN_DIAGONAL);
					
					// CHAIN_SAME_BLOCK
					if (chainSameBlock > 1)
						s.addScore(chainSameBlock * CHAIN_SAME_BLOCK);
						
					
					// SAME_BLOCK
					Block b = newBoard.getBlock(pos.getBlock());
					int sameBlock = 0;
					for (int i = 0; i <= 8 ; i++)
					{
						if (b.GetTile(i).getColor() == this.getColor())
							sameBlock++;
					}
					if (sameBlock > 1)
						s.addScore(sameBlock * SAME_BLOCK);
					
					
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
