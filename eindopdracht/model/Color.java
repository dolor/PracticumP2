package eindopdracht.model;

public class Color {
	private int color;
	
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static final int YELLOW = 4;
	
	//TODO: Pre and Post conditions
	public Color (int color)
	{
		this.color = color;
	}
	
	public int GetColor()
	{
		return this.color;
	}

}
