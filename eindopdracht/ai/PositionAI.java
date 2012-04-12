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

	/**
	 * Depth of the recursion at which this position is calculated.
	 * @return
	 */
	public int getDepth()
	{
		return this.depth;
	}
	/**
	 * @ensure this.getDepth() == depth
	 * @param score the depth of a certain position (Only used in AI)
	 */
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	
	/**
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
