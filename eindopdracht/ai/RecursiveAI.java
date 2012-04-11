package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;
import eindopdracht.model.Position;
import eindopdracht.model.Row;
import eindopdracht.util.PTLog;

public class RecursiveAI extends AI {
	ArrayList<Integer> players;

	String[] chatLines;
	
	public RecursiveAI(int color, Board board, ArrayList<Integer> players) {
		super(color, board, players);
		this.players = players;
		
		System.out.println(players.toString());
		this.chatLines = new String[] { "Well well.", "Not bad.", "...",
				"I see...", "Hah, now i've got a genius plan!",
				"Did you seriously think that was going to work?",
				"We'll see.", "Hah!" };
	}

	public static final int SCORE_GROUND = 2;
	public static final int ROWS_SCORE = 2;
	public static final long WINNING_MOVE = 6;
	public static final int LOSING_MOVE = 4;
	
	public static final int RECUSION_DEPTH = 6;
	
	//public static final int ROWS_SCORE = 5;
	//public static final int WINNING_MOVE = 1000000000;
	// public static final int LOSING_MOVE = 10000000;

	/**
	 * Gives the board a score. The higher the score the better the current
	 * player is "doing".
	 * 
	 * @param b
	 *            the board
	 * @param color
	 *            color of the player
	 * @require color > 0 && color <= 4
	 * @return the score of the board
	 */
	private int getBoardScore(Board b, int color) {
		int score = 0; // score van de rijen op het bord

		// bereken de score van alle rijen en tel die op
		ArrayList<Row> rows = b.getRows();

		for (Row r : rows) {
			// kijk of hij winnend is
			
			if (r.getLength() >= 4)
			{
				if (r.getColor() == color)
				{
					score += WINNING_MOVE;
					/*else
					{
						score += ROWS_SCORE * (r.getLength()+1) * (r.getLength()+1) * (r.getLength()+1);
					}	*/
				}
				/*else
				{
					score += LOSING_MOVE;
				}*/
			}


			if (r.getColor() == color) {
				if (r.getLength() >= 4) {
					score += WINNING_MOVE;
				} else {
					score += ROWS_SCORE * Math.pow(SCORE_GROUND, r.getLength());
				}

			}
			/*
			 * else // opponent { if (r.getLength() >= 4) { score -=
			 * LOSING_MOVE; } score -= ROWS_SCORE * Math.pow(SCORE_GROUND,
			 * r.getLength()); }
			 */

		}

		return score;

	}

	/**
	 * Calculates the best next move according to the AI.
	 * 
	 * @param color
	 *            the color of the player who is has the next move.
	 * @param depth
	 *            the current depth of the recursion
	 * @param board
	 *            the board
	 * @return the best move
	 */
	private PositionAI getRecusiveSet(int color, int depth, Board board) {
		depth = depth - 1;

		if (depth > 1) {
			PositionAI bestpos = new PositionAI(-1, -1);

			for (int y = 0; y <= 8; y++) {
				for (int x = 0; x <= 8; x++) {

					if (board.getTileXY(x, y).getColor() == 0) {

						PositionAI pos = new PositionAI(x, y);

						// plaats op het board
						board.set(pos.getBlock(), pos.getTile(), color);

						//PTLog.log("AI", "Pos: "+board.getTileXY(x, y).setColor(0, true)+ ", pos " + board.getTileXY(x, y).getColor());
						
						// calculate score
						
						Position recursiveSet = getRecusiveSet(nextPlayerForColor(color), depth-1, board);
						
						board.set(recursiveSet.getBlock(), recursiveSet.getTile(), color);
						
						long score = getBoardScore(board, color) * depth * depth;
						
						// TODO: Doorgeven WINNEND / VERLIESEND, bij verliesend !bestpos bij winnnend !bestpos.
						
						/*if (recursiveSet != null)
						{
							pos.setScore(score + recursiveSet.getScore());
						}
						else
						{
							pos.setScore(score);
						}
						
						//PTLog.log("AI", "Score : "+pos.getScore() + ", recursion depth " + (depth));
						
						if (pos.getScore() > bestpos.getScore())
						{
							bestpos = pos;
							PTLog.log("AI", "bestmove "+bestpos.getScore()+" pos: "+bestpos.getX()+", "+bestpos.getY());
							
						}

						int score = getBoardScore(board, color) * depth;*/

						if (score > bestpos.getScore()) {
							bestpos = pos;

							pos.setScore(getRecusiveSet(
									nextPlayerForColor(color), depth, board)
									.getScore() + score);
						}

						// maak de zet ongedaan

						board.set(pos.getBlock(), pos.getTile(), 0);
						board.set(recursiveSet.getBlock(), recursiveSet.getTile(), 0);
						
						// maak de zet ongedaan
						//PTLog.log("AI", "Pos: "+board.getTileXY(x, y).setColor(0, true)+ ", pos " + board.getTileXY(x, y).getColor());
						
						
						/*if (score >= bestpos.getScore())
						{
							bestpos = pos;
							
							Position nextMove = getRecusiveSet(nextPlayerForColor(color), depth - 1, board);
							
							if (nextMove != null)
							{
								pos.setScore(nextMove.getScore() + score);
							}
							else
							{
								pos.setScore(score);
							}
						}*/
						/*
						
						pos.setScore(score);
						
						// winnend				
						if (pos.getScore() >= WINNING_MOVE)
						{
							bestpos = pos;
						}
						else // kijk recursief
						{
							Position nextMove = getRecusiveSet(nextPlayerForColor(color), depth - 1, board);
							
							if (nextMove != null)
							{
								pos.setScore(nextMove.getScore()*depth + score);
							}
							else
							{
								pos.setScore(score);
							}
							
							
							if (pos.getScore() >= bestpos.getScore())
							{
								bestpos = pos;
							}
						}*/
						
						
					}
				}
			}
			return bestpos;
		}
		else
		{
			return new PositionAI(-1, -1);
		}


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

	@Override
	public void calculateTurn(Turn turn) {
		/*try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}*/

		// kies een willekeurig blok
		int block = (int) Math.round(Math.random() * 8);
		turn.setBlock(block);

		// draai hem in een willekeurige richting
		int rotation = (int) Math.round(1 + Math.random());
		turn.setRotation(rotation);
	}

	@Override
	public void calculateSet(Set set) {
		Board nB = this.board.deepCopy();

		PositionAI pos = getRecusiveSet(this.getColor(), RECUSION_DEPTH, nB);
		PTLog.log("RecursiveAI",
				"Calculated set: " + pos.getX() + ", " + pos.getY()
						+ " score: " + pos.getScore());
		set.setBlock(pos.getBlock());
		set.setTile(pos.getTile());
	}

}
