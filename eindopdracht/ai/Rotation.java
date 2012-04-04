package eindopdracht.ai;

public class Rotation {
	
	int block;
	int rotation;
	int depth;
	int color;
	
	public Rotation (int block, int rotation)
	{
		this.block = block;
		this.rotation = rotation;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public int getDepth()
	{
		return this.depth;
	}
	
	public int getBlock()
	{
		return this.block;
	}
	
	public int getRotation()
	{
		return this.getRotation();
	}

	public void setColor(int color) {
		this.color = color;
	}
	public int getColor()
	{
		return this.color;
	}
}
