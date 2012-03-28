package eindopdracht.ai.intelligent;

import eindopdracht.ai.Position;

public class IntelligentSet {

	int score;
	Position pos;
	boolean otherplayermove;
	
	public IntelligentSet(Position pos)
	{
		this.pos = pos;
	}
	
	public void setOtherPlayerMove(boolean otherplayermove)
	{
		this.otherplayermove = otherplayermove;
	}
	public boolean isOtherPlayerMove()
	{
		return this.otherplayermove;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	public int getScore()
	{
		return this.score;
	}
	public void addScore(int add)
	{
		setScore(this.getScore() + add);
	}
	
	public Position getPosition()
	{
		return pos;
	}
}
