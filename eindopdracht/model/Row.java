package eindopdracht.model;

import java.util.ArrayList;

import eindopdracht.ai.Position;

public class Row {

	ArrayList<Position> posities;
	int color;
	
	public Row()
	{
		this.posities = new ArrayList<Position>();
	}
	
	public int getColor()
	{
		return this.color;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public void addPosition(Position pos)
	{
		this.posities.add(pos);
	}
	public ArrayList<Position> getPositions()
	{
		return posities;
	}
	
}
