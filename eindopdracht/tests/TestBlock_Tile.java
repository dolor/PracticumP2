package eindopdracht.tests;
import eindopdracht.model.*;
public class TestBlock_Tile {
	public static void main (String[] args)
	{
		// maak een block aan
		
		Block block = new Block();
		
		block.getTile(1).setColor(Color.RED);
		block.getTile(2).setColor(Color.GREEN);
		block.getTile(3).setColor(Color.RED);
		block.getTile(4).setColor(Color.BLUE);
		block.getTile(5).setColor(Color.YELLOW);
		block.getTile(6).setColor(Color.GREEN);
		block.getTile(7).setColor(Color.BLUE);
		block.getTile(8).setColor(Color.YELLOW);
		block.getTile(0).setColor(Color.BLUE);
		
		block.drawBlock();
		System.out.println("--------");
		block.Turn(Block.CW);
		
		block.drawBlock();
		System.out.println("--------");
		block.Turn(Block.CCW);
		
		block.drawBlock();
		
	}

}
