package eindopdracht.client.model.player;

import java.util.Observable;
import java.util.Observer;

import eindopdracht.client.Game;

public class Player implements Observer  {
	int color;
	String name;
	int state;
	Game game;
	boolean localPlayer;
	
	public static final int TURNING = 2;
	public static final int SETTING = 1;
	public static final int IDLE = 0;
	
	public Player()
	{
		this.state = IDLE;
		this.game = null;
	}
	
	/**
	 * Should be called by subclasses so the network knows if its a local player.
	 * Only sets/turns by local players are broadcasted across the network.
	 * @require local if this is a player commanded from this client
	 */
	public final void setLocal(boolean local) {
		this.localPlayer = local;
	}
	
	/**
	 * Called by network to see if this is a locally controlled player
	 * @return
	 */
	public final boolean isLocal() {
		return localPlayer;
	}
	
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	public Game getGame()
	{
		return this.game;
	}
	
	public int getState()
	{
		return this.state;
	}
	public void setState(int state)
	{
		if (state >= 0 && state <= 2)
		{
			this.state = state;
		}
	}
	
	
	/**
	 * @ensure 0 <= color <= 4
	 * @param color
	 */
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Player object with not yet implemented update method got a set/turn!");
	}

}
