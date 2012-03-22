package eindopdracht.client;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.model.Board;

import eindopdracht.model.Set;
import eindopdracht.model.Turn;

import eindopdracht.model.player.Player;


public class Game extends Observable{
	
	ArrayList<Player> players;
	Board board;
	Player settingPlayer;
	
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
		
		this.settingPlayer = players.get(0); // eerste speler is aan de beurt
	}
	/**
	 * maakt een set object aan en stuurt dat naar observers
	 */
	public void giveSet()
	{
		Set set = new Set(this.getSettingPlayer());
		
		this.setChanged();
		this.notifyObservers(set);	
	}
	/** geeft een turn object naar de observers
	 *
	 */
	public void giveTurn()
	{
		Turn turn = new Turn(this.getSettingPlayer());
		
		this.setChanged();
		this.notifyObservers(turn);	
	}
	
	public Player getSettingPlayer()
	{
		return this.settingPlayer;
	}
	
	public void setSettingPlayer(Player player)
	{
		if (this.players.contains(player))
		{
			this.settingPlayer = player;
		}
	}
	public void nextSettingPlayer()
	{
		int index = players.indexOf(getSettingPlayer());
		
		if (index + 1 < players.size()) // als de volgende index binnen de range ligt
		{
			index++;
		}
		else // begin bij het begin.
		{
			index = 0;
		}
		
		setSettingPlayer(players.get(index));
	}
	
	public void set(Set set)
	{
		//TODO set verwerken
	}
	
	public void turn(Turn turn)
	{
		//TODO: turn verwerken
	}
	
}
