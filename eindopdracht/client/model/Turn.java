package eindopdracht.client.model;

import eindopdracht.client.model.player.Player;


public class Turn {

	int block;
	int rotation;
	Player player;
	boolean executed;
	boolean valid;
	public Turn(Player player)
	{
		this.block = -1;
		this.rotation = -1;
		this.player = player;	
		this.executed = false;
		this.valid = true;
	}
	
	public void setExecuted(boolean executed)
	{
		this.executed = executed;
	}
	public boolean isExecuted()
	{
		return this.executed;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}
	public boolean getValid()
	{
		return this.valid;
	}

	public int getBlock()
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

	@Override
	public String toString() {
		return "Player: " + getPlayer().getName() + ", Block: " + getBlock() + ", Direction: " + getRotation() + ", Executed: " + isExecuted();
	}
}
