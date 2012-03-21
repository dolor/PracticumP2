package eindopdracht.tests;
import eindopdracht.model.*;
public class TestBlock_Tile {
	public static void main (String[] args)
	{
		// maak een block aan
		
		Block block = new Block();
		
		block.GetTile(1).SetColor(Color.RED);
		block.GetTile(2).SetColor(Color.GREEN);
		block.GetTile(3).SetColor(Color.RED);
		block.GetTile(4).SetColor(Color.BLUE);
		block.GetTile(5).SetColor(Color.YELLOW);
		block.GetTile(6).SetColor(Color.GREEN);
		block.GetTile(7).SetColor(Color.BLUE);
		block.GetTile(8).SetColor(Color.YELLOW);
		block.GetTile(0).SetColor(Color.BLUE);
		
		block.DrawBlock();
		System.out.println("--------");
		block.Turn(block.CW);
		
		block.DrawBlock();
		System.out.println("--------");
		block.Turn(block.CCW);
		
		block.DrawBlock();
		
	}

}
