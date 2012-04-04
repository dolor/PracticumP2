package eindopdracht.model;

import eindopdracht.util.PTLog;



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
	
	public static int getOtherRotation(int rotation)
	{
		if (rotation == CW)
		{
			return CCW;
		}
		else
		{
			return CW;
		}
	}
	
	/**
	 * Rotate the Block, counterclockwise or clockwise;
	 * @require 1 <= rotation <= 2
	 * @ensure The block is rotated
	 * @param rotation
	 */
	public boolean Turn(int rotation)
	{
		if (rotation >= 1 && rotation <= 2)
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


				newTiles[newI] = tiles[i];
			}

			tiles = newTiles;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @require tile >= 0 && tile <= 8
	 * @ensure getTile(tile) != null
	 * @param tile the tile which has to be returned
	 * @return the wanted tile
	 */
	public Tile getTile(int tile)
	{
		return tiles[tile];
	}
	
	/**
	 * Put a tile on a given spot.
	 * @ensure this.getTile(tile) == t
	 * @param t tile who has to be set there
	 * @param tile the position where the tile is located
	 */
	public void setTile(Tile t, int tile)
	{
		tiles[tile] = t;
	}
	
	// for debug purposes
	public void DrawBlock()
	{
		for (int i = 0; i <= 2; i++)
		{
			System.out.println(tiles[i*3+0].getColor() + "|" + tiles[i*3+1].getColor() + "|" + tiles[i*3+2].getColor());
		}
	}
	
	/**
	 * Gives a deep copy of the board. Every object is copied.
	 * @ensure every deepCopy().getTileXY(x,y).getColor() == this.getTileXY(x,y).getColor()
	 * @return Deep copy of this board
	 */
	public Block deepCopy()
	{
		Block b = new Block();
		for (int i = 0; i <= 8; i++)
		{
			b.setTile(this.getTile(i).deepCopy(), i);
		}
		return b;
	}
	

}
