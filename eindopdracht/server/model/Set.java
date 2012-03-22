package eindopdracht.server.model;

import eindopdracht.server.Player;

public class Set {
	Player player;
	int block;
	int tile;
	boolean executed;
	public Set(Player player)
	{
		this.player = player;
		this.block = -1;
		this.tile = -1;
		this.executed = false;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public int getBlock()
	{
		return this.block;
	}
	public void setBlock(int block)
	{
		this.block = block;
	}
	
	public int getTile()
	{
		return this.tile;
	}
	
	public void setTile(int tile)
	{
		this.tile = tile;
	}
	
	public void setExecuted(boolean executed)
	{
		this.executed = executed;
	}
	public boolean getExecuted()
	{
		return this.executed;
	}


}
