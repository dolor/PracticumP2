package eindopdracht.ai;

import java.util.ArrayList;
import java.util.Locale;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.model.Position;
import eindopdracht.util.PTLog;

public class RandomAI extends AI {

	private static int sleepTime = 5;
	// Used because these dumb AIs are so fast there gets a race condition in
	// the network. Shouldn't be necessary with the recursive AI

	public RandomAI(int color, Board board, ArrayList<Integer> players) {
		super(color, board, players);
		this.chatLines = new String[]{ "hurr", "durr", "hmprf", "grmpf", "eh", "ah", "oh", "ehe", "derp", "darp", "durp"};
	}

	@Override
	public void calculateTurn(Turn turn) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}

		// kies een willekeurig blok
		int block = (int) Math.round(Math.random() * 8);
		turn.setBlock(block);

		// draai hem in een willekeurige richting
		int rotation = (int) Math.round(1 + Math.random());
		turn.setRotation(rotation);
	}

	@Override
	public void calculateSet(Set set) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}

		// maak een lijst van mogelijke zetten
		ArrayList<Position> positions = new ArrayList<Position>();

		for (int x = 0; x <= 8; x++) {
			for (int y = 0; y <= 8; y++) {
				if (board.getTileXY(x, y).getColor() == 0) {
					positions.add(new Position(x, y));
				}
			}
		}

		// kies een willekeurige
		int index = (int) Math.round(Math.random() * (positions.size() - 1));
		Position pos = positions.get(index);

		// vul de set

		//Kijk of je moet chatten
		if (Math.random() > 0.8)
			this.chat();
		set.setBlock(pos.getBlock());
		set.setTile(pos.getTile());
	}

	/**
	 * Returns a random chatline for this AI
	 * @ensure null if not implemented in AI
	 * @return
	 */
	public String chat() {
		int numberOfWords = (int)Math.floor(Math.sqrt(Math.random() * 9));
		String full = "";
		
		for (int i = 0; i < numberOfWords; i++)
			full = full + super.chat() + " ";
		
		full = full.trim();
		
		if (full != null && !full.equals(""))
			full = Character.toUpperCase(full.charAt(0)) + full.substring(1);
		//Capitalize first word
		
		int ending = (int)(Math.random() * 4);
		full = full + chatEndings[ending];
		
		return full;
	}
}
