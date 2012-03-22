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
		
		this.settingPlayer.setState(Player.SETTING);
	}
	/**
	 * maakt een set object aan en stuurt dat naar observers
	 */
	public void giveSet()
	{
		Set set = new Set(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.SETTING);
		
		this.setChanged();
		this.notifyObservers(set);	
	}
	/** geeft een turn object naar de observers
	 *
	 */
	public void giveTurn()
	{
		Turn turn = new Turn(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.TURNING);
		
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
			this.getSettingPlayer().setState(Player.IDLE);
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
		
		this.getSettingPlayer().setState(Player.IDLE); // zet de player op idle
		
		this.setSettingPlayer(players.get(index));
		
		this.giveSet();
		
		
	}
	
	public void set(Set set)
	{
		if (set.getPlayer() == this.getSettingPlayer() && this.getSettingPlayer().getState() == Player.SETTING && !set.getExecuted()) // hij is aan de beurt
		{
			// de zet uitvoeren
			set.setValid(this.board.Set(set.getBlock(), set.getTile(), set.getPlayer().getColor()));
			set.setExecuted(true);
			
			this.setChanged();
			this.notifyObservers(set); // vertel iedereen dat de zet is uitgevoerd
			
			// deel een nieuwe turn uit
			this.giveTurn();
		}
	}
	
	public void turn(Turn turn)
	{
		if (turn.getPlayer() == this.getSettingPlayer() && this.getSettingPlayer().getState() == Player.TURNING && !turn.getExecuted()) // hij is aan de beurt
		{
			//TODO set verwerken
			turn.setValid(this.board.Turn(turn.getBlock(), turn.getRotation()));
			turn.setExecuted(true);
			
			this.setChanged();
			this.notifyObservers(turn); // vertel iedereen dat de zet is uitgevoerd
			
			// nieuwe player is aan de beurt
			this.nextSettingPlayer();
		}
	}
	
}
