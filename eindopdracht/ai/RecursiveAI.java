package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;
import eindopdracht.model.Position;
import eindopdracht.model.Row;

public class RecursiveAI extends AI{
	ArrayList<Integer> players;
	
	public RecursiveAI(int color, Board board, ArrayList<Integer> players) {
		super(color, board, players);
		this.players = players;
		// TODO Auto-generated constructor stub
	}

	public static final int SCORE_GROUND = 2;
	public static final int ROWS_SCORE = 5;
	public static final int WINNING_MOVE = 1000000000;
	public static final int LOSING_MOVE = 10000000;
	
	public static final int RECUSION_DEPTH = 3;
	
	private int getBoardScore(Board b, int color)
	{
		int score = 0; // score van de rijen op het bord
		
		// bereken de score van alle rijen en tel die op
		ArrayList<Row> rows = b.getRows();
		
		for (Row r : rows)
		{
			// kijk of hij winnend is
			
			
			if (r.getColor() == color)
			{
				if (r.getLength() >= 4)
				{
					score += WINNING_MOVE;
				}
				else
				{
					score += ROWS_SCORE * Math.pow(SCORE_GROUND, r.getLength());
				}
			
				
			}
			else // opponent
			{
				if (r.getLength() >= 4)
				{
					score -= LOSING_MOVE;
				}
				score -= ROWS_SCORE * Math.pow(SCORE_GROUND, r.getLength());
			}
		}
					
		return score;
		
	}
	
	private Position getRecusiveSet(int color, int depth, Board board)
	{
		depth = depth - 1;
		
		if (depth > 1)
		{
			Position bestpos = new Position(-1, -1);
			
			
			for (int y = 0; y <= 8; y++)
			{
				for (int x = 0; x <= 8; x++)
				{
					
					if (board.getTileXY(x, y).getColor() == 0)
					{
						Position pos = new Position(x, y);
						
						// plaats op het board
						board.set(pos.getBlock(), pos.getTile(), color);
						
						int score = getBoardScore(board, color) * depth;
						
						if (score > bestpos.getScore())
						{
							bestpos = pos;
	
							pos.setScore(getRecusiveSet(nextPlayerForColor(color), depth, board).getScore() + score);
						}
						
						// maak de zet ongedaan
						board.set(pos.getBlock(), pos.getTile(), 0);
					}
				}
			}
			return bestpos;
		}
		else
		{
			return new Position(0, 0, 0);
		}
			
		
	}
	
	/**
	 * Determines which color would be player after the given color
	 * @param color current player
	 * @return next player
	 */
	public int nextPlayerForColor(int color) {
		for (int player :this.players) {
			if (player == color) {
				if (players.indexOf(player) == players.size()-1) {
					//was the last player
					return players.get(0);
				} else {
					return players.get(players.indexOf(player) + 1);
				}
			}
		}
		return players.get(0);
	}
	
	@Override
	public void calculateTurn(Turn turn) {
		// TODO Auto-generated method stub
		Board nB = this.board.deepCopy();
		
		Position pos = getRecusiveSet(this.getColor(), RECUSION_DEPTH, nB);
		
		
	}

	@Override
	public void calculateSet(Set set) {
		// TODO Auto-generated method stub
		
	}

}
