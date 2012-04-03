package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.ai.intelligent.IntelligentSet;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;
import eindopdracht.model.Position;

public class IntelligentAI extends AI {
	
	IntelligentAI otherplayersAI;
	
	private static int sleepTime = 15;
	// Used because these dumb AIs are so fast there gets a race condition in
	// the network. Shouldn't be necessary with the recursive AI

	public IntelligentAI(int color, Board board, ArrayList<Integer> players) { // TODO: ANDERE SPELERS DOORGEVEN
		super(color, board, players);
		
		
		// maak een AI aan voor de andere spelers
		if (otherplayers.size() > 0)
			otherplayersAI = new IntelligentAI(getOtherPlayers().get(0), this.getBoard(), this.getOtherPlayers());
		else
			otherplayersAI = null;
	}
	
	protected IntelligentAI getOtherPlayersAI()
	{
		return this.otherplayersAI;
	}
	
	// zetten voor beide
	// aantal > 1
	public static final int INSTANT_WIN = 10000000; // Instant win, over 9000
	
	// Scores voor zetten	
	public static final int CENTER_CENTER = 50; // center van het speelveld
	public static final int CENTER_OTHER = 2; // center van een ander block
	public static final int CHAIN_SAME_BLOCK = 0; // reeks op hetzelfde block * aantal (uiteraard max 3)
	public static final int CHAIN_DIAGONAL = 30; // reeks diagonaal * aantal
	public static final int CHAIN_HORIZONTAL = 30; // reeks horizontaal * aantal 
	public static final int CHAIN_VERTICAL = 30; // reeks verticaal * aantal
	public static final int SAME_BLOCK = 0; // aantal ballen op hetzelfde block * aantal
	public static final int OTHER_PLAYER_MOVE = -1; // aftrek voor een move die berekent is voor de andere spelers
	

	// voor turns
	public static final int BREAK_OPPONENT_CHAIN = 5; // breekt een reeks van de tegenstander door * aantal
	public static final int CREATE_CHAIN = 7; // creeer een keten voor jezelf * aantal
	
	public int countChain_Diagonal(Board b, Position pos, IntelligentSet set)
	{
		int x = pos.getX();
		int y = pos.getY();
		
		// CHAIN_DIAGONAL
		int chainLength = 1;
		// check de borders
		int xL = pos.getX()-4;
		if (xL < 0)
			xL = 0;
		int xR = xL + 4;
		if (xR > 8)
			xR = 8;

		int yU = pos.getY()-4;
		if (yU < 0)
			yU = 0;
		int yD = pos.getY()+4;
		if (yD > 8)
			yD = 8;

		// CHECK LEFT_UP
		for (int yI = y; yI >= yU; yI--)
		{
			for (int xI = x; xI >= xL; xI--)
			{
				if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
				{
					chainLength++;
					if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
						set.setSameBlock(set.getChainSameBlock() + 1);
				}
				else
				{
					break;
				}
			}
		}

		// CHECK LEFT_DOWN
		for (int yI = y; yI <= yD; yI++)
		{
			for (int xI = x; xI >= xL; xI--)
			{
				if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
				{
					chainLength++;
					if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
						set.setSameBlock(set.getChainSameBlock() + 1);
				}
				else
				{
					break;
				}
			}
		}

		// CHECK RIGHT_UP
		for (int yI = y-1; yI >= yU; yI--)
		{
			for (int xI = x; xI <= xR; xI++)
			{
				if ( b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
				{
					chainLength++;
					if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
						set.setSameBlock(set.getChainSameBlock() + 1);
				}
				else
				{
					break;
				}
			}
		}

		// CHECK RIGHT_DOWN
		for (int yI = y+1; yI <= yD; yI++)
		{
			for (int xI = x; xI <= xR; xI++)
			{
				if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)

				{
					chainLength++;
					if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
						set.setSameBlock(set.getChainSameBlock() + 1);
				}
				else
				{
					break;
				}
			}
		}
		
		return chainLength;
	}
	
	public int countChain_Vertical(Board b, Position pos, IntelligentSet set)
	{
		int chainLength = 1;
		int y = pos.getY();
		int x = pos.getX();
		
		// CHAIN_VERTICAL
		int yL = pos.getY()-4; // maximum x aan de linker kant
		if (yL < 0)
			yL = 0;
		int yR = yL + 4; // maximim x aan de rechterkant
		if (yR > 8)
			yR = 8;
		int xI = x;

		// kijk links
		for (int yI = y; y >= yL; y--)
		{
			if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
			{
				chainLength++;
				if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
					set.setSameBlock(set.getChainSameBlock() + 1);
			}
			else
			{
				break;
			}
		}
		for (int yI = y; y <= yR; y++)
		{
			if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
			{
				chainLength++;
				if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
					set.setSameBlock(set.getChainSameBlock() + 1);
			}
			else
			{
				break;
			}
		}
		
		return chainLength;
	}
	
	public int countChain_Horizontal(Board b, Position pos, IntelligentSet set)
	{
		int chainLength = 1;
		int y = pos.getY();
		int x = pos.getX();
		
		int xL = pos.getX()-4; // maximum x aan de linker kant
		if (xL < 0)
			xL = 0;
		int xR = xL + 4; // maximim x aan de rechterkant
		if (xR > 8)
			xR = 8;
		int yI = y;
		// kijk links
		for (int xI = x; x >= xL; x--)
		{
			if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)
			{
				chainLength++;
				if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
					set.setSameBlock(set.getChainSameBlock() + 1);
			}
			else
			{
				break;
			}
		}
		for (int xI = x; x <= xR; x++)
		{
			if (b.getTileXY(xI, yI).getColor() == this.getColor() && xI != x && yI != y)

			{
				chainLength++;
				if (new Position(xI, yI).getBlock() == pos.getBlock() && set != null)
					set.setSameBlock(set.getChainSameBlock() + 1);
			}
			else
			{
				break;
			}

		}
		
		return chainLength;
	}
	
	public int countSameBlock(Board b, Position pos, IntelligentSet set)
	{
		// SAME_BLOCK
		Block block = b.getBlock(pos.getBlock());
		int sameBlock = 0;
		for (int i = 0; i <= 8 ; i++)
		{
			if (block.getTile(i).getColor() == this.getColor())
				sameBlock++;
		}
		
		return sameBlock;
	}
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
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {}
		
		RandomAI r = new RandomAI(this.getColor(), this.getBoard(), this.getOtherPlayers());
		r.calculateTurn(turn);
	}
	
	private void printLine(String line)
	{
		System.out.println("AI:\t\t"+line);
	}
	
	public ArrayList<IntelligentSet> getOptions()
	{
		// lijst van alle zetten
		ArrayList<IntelligentSet> options = new ArrayList<IntelligentSet>();

		for (int x = 0; x <= 8; x++)
		{
			for (int y = 0; y <= 8; y++)
			{
				if (board.getTileXY(x, y).getColor() == 0) // kan ik hier nog wel wat zetten?
				{
					// de zet is legaal

					// maak een intelligent zet met positie aan
					Position pos = new Position(x,y);
					
					// Maak een board om aan te rekenen
					Board newBoard = board.deepCopy();
					
					// maak een zet aan
					IntelligentSet s = new IntelligentSet(pos, newBoard);
					
					// zet op het nieuwe bord
					newBoard.set(pos.getBlock(), pos.getTile(), this.getColor());

					// INSTANT_WIN
					if (newBoard.GetWinners().contains(this.getColor()))
					{
						s.setInstantWin();
						printLine("Player " + this.getColor() + ", Instant win detected at X: "+x+", Y: "+y);
					}					

					// CENTER_CENTER
					if (x == 4 && y == 4)
					{
						s.setCenterCenter();
					}
					// CENTER_OTHER
					else if (pos.getTile() == 4)
					{
						s.setCenterOther();
					}
					
					// CHAIN_DIAGONAL
					s.setChainDiagonal(this.countChain_Diagonal(newBoard, pos, s));
					
					// CHAIN_VERTICAL
					s.setChainVertical(this.countChain_Vertical(newBoard, pos, s));
					
					// CHAIN_HORIZONTAL
					s.setChainHorizontal(this.countChain_Horizontal(newBoard, pos, s));
					
					// SAME_BLOCK
					s.setSameBlock(this.countSameBlock(newBoard, pos, s));
					
					s.setOtherPlayerMove(false);
					
					s.calculateScore();

					options.add(s);
				}
			}
		}
		
		// options van de andere speler AI
		
		if (getOtherPlayersAI() != null) // de AI bestaat
		{
			for (IntelligentSet s : getOtherPlayersAI().getOptions())
			{
				s.setOtherPlayerMove(true); // move is gemaakt voor een andere speler
				options.add(s);
			}
		}
		
		return options;
	}

	@Override
	public void calculateSet(Set set) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {}
		
		ArrayList<IntelligentSet> options = getOptions();
		// neem de hoogste en return die
		IntelligentSet pick = options.get(0);
		for (IntelligentSet s : options)
		{
			if (s.isOtherPlayerMove()) // als het van een andere speler is krijg men punten aftrek
			{
				s.setScore(s.getScore() + OTHER_PLAYER_MOVE);
			}
			if (s.getScore() > pick.getScore())
			{
				pick = s;
			}
		}
		Position pos = pick.getPosition();
		this.printLine("Picked score: "+pick.getScore()+", OtherPlayerMove: "+pick.isOtherPlayerMove());
		this.printLine("X "+pos.getX()+", Y "+pos.getY()+", BLOCK "+pos.getBlock()+", TILE "+pos.getTile());
		 
		// stel pick in in set
		set.setBlock(pick.getPosition().getBlock());
		set.setTile(pick.getPosition().getTile());
	}

}
