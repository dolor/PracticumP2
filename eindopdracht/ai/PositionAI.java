package eindopdracht.ai;

import eindopdracht.model.Position;

public class PositionAI extends Position {

	long score;
	int depth;
	
	public PositionAI(int x, int y) {
		super(x, y);
		this.score = 0;
		this.depth = 0;
	}

	
	public int getDepth()
	{
		return this.depth;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	
	/*/**
	 * @ensure getScore() >= 0 && getScore() <= 4
	 * @return the score of a certain position (Only used in AI)
	 */
	public long getScore()
	{
		return this.score;
	}
	/**
	 * @ensure this.getScore() == score
	 * @param score the score of a certain position (Only used in AI)
	 */
	public void setScore(long score)
	{
		this.score = score;
	}
	
}
