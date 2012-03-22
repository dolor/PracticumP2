package eindopdracht.client;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.model.Board;

public class Game extends Observable {
	
	ArrayList<Player> players;
	Board board;
	
	/**
	 * Maakt een game aan.
	 * @param players De spelers die mee doen. Lijst staat op volgorde.
	 */
	public Game (ArrayList<Player> players)
	{
		this.players = players;
		
		// geef spelers hun kleur
		int color = 1;
		for (Player player : players)
		{
			// voeg als observer toe
			this.addObserver(player);
			
			
			// geef kleur
			player.setColor(color);
			color++;
		}	
		this.board = new Board();
		
		
		
	}
	
	
}
