package eindopdracht.ai;

import eindopdracht.model.Board;
import eindopdracht.model.Color;
import eindopdracht.model.Position;
import eindopdracht.util.PTLog;

//TODO: klasse verwijderen bij uiteindelijke inlevering
public class ThreadedSet extends Thread{

	Board board;
	int playerColor;
	int recursionDepth;
	Position bestMove;
	boolean calculationDone;
	RecursiveAI2 ai;
	int beginY;

	ThreadedSet(Board b, int playerColor, int recursionDepth, RecursiveAI2 ai, int beginY) {
		// Create a new, second thread
		super("ThreadedSet");
		this.board = b;
		this.playerColor = playerColor;
		this.recursionDepth = recursionDepth;
		this.ai = ai;
		this.beginY = beginY;
	}

	
	public Position bestMove()
	{
		return this.bestMove();
	}
	public boolean isCalculationDone()
	{
		return this.calculationDone;
	}
	
	
	// This is the entry point for the second thread.
	public void run() {
		PositionAI returnPos = null;
		int y = beginY;
		for (int x = 0; x <= 8; x++)
		{
			if (board.getTileXY(x, y).getColor() == Color.EMPTY) // valkje = leeg
			{
				// kijk of de zet winnen is
				PositionAI p = new PositionAI(x, y);

				if (recursionDepth == 0)
				{
					bestMove = p;
					return;
				}

				// plaats de zet
				board.set(p.getBlock(), p.getTile(), playerColor, true);

				// haal de uitkomst van de zet op					
				int uitkomst = ai.geefUitkomst(board, playerColor) ;



				if (uitkomst == ai.WINNEND)
				{
					// als een zet winnend is, meteen returnen, zet de diepte van de victorie op p
					//PTLog.log("RecursiveAI", "Winning move at "+x+","+y);
					p.setDepth(recursionDepth);
					p.setColor(playerColor);
					//b.drawBoard();
					board.set(p.getBlock(), p.getTile(), Color.EMPTY, true);

					bestMove = p;
					return;
				}
				// de eerst volgende zet voor de tegestander is bij deze positie winnend
				else if (recursionDepth == ai.RECURSION_DEPTH && ai.geefUitkomst(board, ai.nextPlayerForColor(playerColor)) == ai.WINNEND)
				{
					p.setDepth(recursionDepth);
					board.drawBoard();
					PTLog.log("RecursiveAI", "Block instant win of opponent, depth "+recursionDepth);
					board.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
					bestMove = p;
					return;
				}
				else if (uitkomst == ai.ONBESLIST && recursionDepth >= 1)
				{

					PositionAI recPos = getBestMove(board, ai.nextPlayerForColor(playerColor), recursionDepth-1);

					if (recPos != null)
					{
						if (returnPos == null  || recPos.getDepth() > returnPos.getDepth() && recPos.getColor() == playerColor)
						{
							//returnPos = recPos;
							returnPos = p;
						}
					}

					//PTLog.log("RecursiveAI", "recPos: "+recPos.getDepth()+" returnPos: "+returnPos.getDepth());

				}

				board.set(p.getBlock(), p.getTile(), Color.EMPTY, true);
				// maak de zet weer leeg;


			}
		}
		bestMove = returnPos;
		return;
	}
	public PositionAI getBestMove(Board b, int playerColor, int recursionDepth)
	{
		return ai.getBestMove(b, playerColor, recursionDepth);
	}
}

