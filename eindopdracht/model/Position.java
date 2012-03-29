package eindopdracht.model;

public class Position {

	int x;
	int y;
	int color;
	int score;
	
	public Position(int x, int y)
	{
		this(x, y, -1);
	}
	
	public Position(int x, int y, int color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.score = 0;
	}
	
	public int getScore()
	{
		return this.score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	
	
	public int getColor()
	{
		return this.color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public int getBlock()
	{
		// kijk welk block het is
		int BlockCol = (int) Math.floor(x / 3);
		int BlockRow = (int) Math.floor(y / 3);
		
		//TODO: Testen of dit klopt
		return BlockRow * 3 + BlockCol;
	}
	
	public int getTile()
	{
		int TileCol = x % 3;
		int TileRow = y % 3;
		return TileRow * 3 + TileCol;
	}
}
