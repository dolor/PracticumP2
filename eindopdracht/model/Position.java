package eindopdracht.model;

/**
 * Container class for a position in the game. Supports both X/Y and Block/Tile
 * @author mickvdv
 *
 */
public class Position {

	int x;
	int y;
	int color;
	long score;
	
	public Position(int x, int y)
	{
		this(x, y, -1);
	}
	
	/**
	 * @require x >= 0 && x <= 8
	 * 			y >= 0 && y <= 8
	 * 			color >= 0 && color <= 4
	 * @ensure 	this.getX() == x
	 * 			this.getY() == y
	 * 			this.getColor() == color
	 * @param x the x position
	 * @param y the y position
	 * @param color the color
	 */
	public Position(int x, int y, int color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.score = 0;
	}
	
	/**
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
