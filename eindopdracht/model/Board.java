package eindopdracht.model;

import java.util.ArrayList;

public class Board {
	public static final int DIM = 3;
	
	Block[] blocks;
	
	public Board()
	{
		blocks = new Block[DIM*DIM];
		
		for (int i = 0; i <= Board.DIM*Board.DIM-1; i++)
		{
			blocks[i] = new Block();
		}
	}
	
	public boolean Set(int block, int tile, int color)
	{
		return blocks[block].GetTile(tile).SetColor(color);
	}
	
	public void Turn(int block, int rotation)
	{
		blocks[block].Turn(rotation);
	}
	/**
	 * Kijkt naar het board als geheel met een x van 0 8 en een y van 0 tot 8 0,0 is bovenin
	 * @param x
	 * @param y
	 * @return
	 */
	
	public Tile GetTileXY (int x, int y)
	{
		// kijk welk block het is
		int BlockCol = (int) Math.floor(x / 3);
		int BlockRow = (int) Math.floor(y / 3);
		
		int TileCol = x % 3;
		int TileRow = y % 3;
		//TODO: Testen of dit klopt
		return blocks[BlockRow * 3 + BlockCol].GetTile(TileRow*3 + TileCol);

	}
	
	// board weet alleen of er 5 op een rij is of niet. Het aantal knikkers controlleerd game
	public ArrayList<Integer> GetWinners()
	{
		// ga alle rijen af
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		// check cols
		for (int y = 0; y < 8; y++)
		{
			int count = 0;
			int lastcolor = 0;
			
			for (int x = 0; x < 8; x++)
			{
				
				// haal de kleur van de tegel op
				int color = GetTileXY(x,y).GetColor();
				
				// kijk of die dezelfde kleur is
				if (color == lastcolor)
				{
					count += 1; // doe de count +1
				}
				else
				{
					count = 1;
				}
				
				if (count >= 5) // 5 op een rij
				{
					winners.add(color);
				}
			}
		
		}
		// check rows
		for (int x = 0; x < 8; x++)
		{
			int count = 0;
			int lastcolor = 0;
			
			for (int y = 0; y < 8; y++)
			{
				
				// haal de kleur van de tegel op
				int color = GetTileXY(x,y).GetColor();
				
				// kijk of die dezelfde kleur is
				if (color == lastcolor)
				{
					count += 1; // doe de count +1
				}
				else
				{
					count = 1;
				}
				
				if (count >= 5) // 5 op een rij
				{
					winners.add(color);
				}
			}
			
			// check diagonal
		
		}
		
		return winners;
	}
	
	public boolean GameOver()
	{
		return !GetWinners().isEmpty(); // als de lijst niet leeg is is er een winner en dus game over
	}
	
}
