package eindopdracht.ai;

import java.util.ArrayList;

import eindopdracht.model.Board;
import eindopdracht.model.Set;
import eindopdracht.model.Turn;


public class RandomAI extends AI{

	public RandomAI(int color, Board board) {
		super(color, board);
	}

	@Override
	public void calculateTurn(Turn turn) {
		// kies een willekeurig blok
		int block = (int) Math.round(Math.random()*8);
		turn.setBlock(block);
		
		// draai hem in een willekeurige richting
		int rotation = (int) Math.round(1+Math.random());
		turn.setRotation(rotation);

	}

	@Override
	public void calculateSet(Set set) {
		// maak een lijst van mogelijke zetten
		ArrayList<Position> positions = new ArrayList<Position>();
		
		for (int x = 0; x <= 8; x++)
		{
			for (int y = 0; y <= 8; y++)
			{
				if (board.getTileXY(x, y).getColor() == 0)
				{
					positions.add(new Position(x,y));
				}
			}
		}
		
		// kies een willekeurige
		int index = (int) Math.round(Math.random()*(positions.size()-1));
		Position pos = positions.get(index);
		
		// vul de set
		set.setBlock(pos.getBlock());
		set.setTile(pos.getTile());
	}
	

}
