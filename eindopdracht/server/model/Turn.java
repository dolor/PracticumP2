package eindopdracht.server.model;


public class Turn {

	int block;
	int rotation;
	ServerPlayer player;
	boolean executed;
	public Turn(ServerPlayer player)
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
	
	public ServerPlayer getPlayer()
	{
		return this.player;
	}

	@Override
	public String toString() {
		return "Player: " + getPlayer().getName() + ", Block: " + getBlock() + ", Direction: " + getRotation() + ", Executed: " + getExecuted();
	}
}
