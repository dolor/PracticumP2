package eindopdracht.model.player;

import java.util.Observable;
import java.util.Observer;

import eindopdracht.client.Game;

public class Player implements Observer  {
	int color;
	String name;
	int state;
	Game game;
	
	public static final int TURNING = 2;
	public static final int SETTING = 1;
	public static final int IDLE = 0;
	
	public Player()
	{
		this.state = IDLE;
		this.game = null;
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
		// TODO Auto-generated method stub
		
	}

}
