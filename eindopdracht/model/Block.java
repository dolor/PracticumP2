package eindopdracht.model;
import java.util.*;
public class Block {
	Tile[] tiles;
	public static int CW = 1;
	public static int CCW = 2;
	
	public Block()
	{
		tiles = new Tile[Board.DIM*Board.DIM];
		for (int i = 0; i <= 8; i++)
		{
			tiles[i] = new Tile();
		}
	}

}
