package eindopdracht.model;


public class Block {
	Tile[] tiles;
	public static int CW = 1; // clock wise;
	public static int CCW = 2; // counter clock wise;
	
	public Block()
	{
		tiles = new Tile[Board.DIM*Board.DIM];
		for (int i = 0; i <= Board.DIM*Board.DIM-1; i++)
		{
			tiles[i] = new Tile();
		}
	}
	
	/**
	 * Rotate the Block, counterclockwise or clockwise;
	 * @require 1 <= rotation <= 2
	 * @ensure The block is rotated
	 * @param rotation
	 */
	public void Turn(int rotation)
	{
		Tile[] newTiles = new Tile[Board.DIM*Board.DIM];
		
		for (int i = 0; i <= Board.DIM*Board.DIM-1; i++)
		{
			// kijk eerste welke colom en rij het is
			int col = i % 3;
			int row = (int) Math.floor(i/3);
			
			// kijk waar die moet komen
			int newI = i;
			
			// kijk dan hoe het in het nieuwe tegel blok is
			if (rotation == CW)
			{
				
				newI = (col) * 3 + Math.abs(row-2);
			}
			else if (rotation == CCW)
			{
				newI = Math.abs((col)-2) * 3 + row;
			}
			//System.out.println("i = " + i + ", col = " + col + ", row = " + row + ", newI = " + newI);
			

			newTiles[newI] = tiles[i];
		}
		
		tiles = newTiles;
	}
	
	public Tile GetTile(int tile)
	{
		return tiles[tile];
	}
	
	// for debug purposes
	public void DrawBlock()
	{
		for (int i = 0; i <= 2; i++)
		{
			System.out.println(tiles[i*3+0].getColor() + "|" + tiles[i*3+1].getColor() + "|" + tiles[i*3+2].getColor());
		}
	}
	

}
