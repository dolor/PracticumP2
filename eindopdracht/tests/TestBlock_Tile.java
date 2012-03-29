package eindopdracht.tests;
import eindopdracht.model.*;
public class TestBlock_Tile {
	public static void main (String[] args)
	{
		// maak een block aan
		
		Block block = new Block();
		
		block.getTile(1).SetColor(Color.RED);
		block.getTile(2).SetColor(Color.GREEN);
		block.getTile(3).SetColor(Color.RED);
		block.getTile(4).SetColor(Color.BLUE);
		block.getTile(5).SetColor(Color.YELLOW);
		block.getTile(6).SetColor(Color.GREEN);
		block.getTile(7).SetColor(Color.BLUE);
		block.getTile(8).SetColor(Color.YELLOW);
		block.getTile(0).SetColor(Color.BLUE);
		
		block.DrawBlock();
		System.out.println("--------");
		block.Turn(Block.CW);
		
		block.DrawBlock();
		System.out.println("--------");
		block.Turn(Block.CCW);
		
		block.DrawBlock();
		
	}

}
