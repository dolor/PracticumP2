package eindopdracht.ai;

public class Position {

	int x;
	int y;
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
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
