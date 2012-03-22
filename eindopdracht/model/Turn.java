package eindopdracht.model;

import eindopdracht.model.player.Player;


public class Turn {

	int block;
	int rotation;
	Player player;
	boolean executed;
	public Turn(Player player)
	{
		this.block = -1;
		this.rotation = -1;
		this.player = player;	
		this.executed = false;
	}
	
	public void setExecuted(boolean executed)
	{
		this.executed = executed;
	}
	public boolean getExecuted()
	{
		return this.executed;
	}
	
	public int GetBlock()
	{
		return this.block;
	}
	public void setBlock(int block)
	{
		this.block = block;
	}
	
	public int getRotation()
	{
		return this.rotation;
	}
	
	public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
}
