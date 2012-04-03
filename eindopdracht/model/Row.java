package eindopdracht.model;

import java.util.ArrayList;

/**
 * Contains the positions on a row. Mostly used in the AI.
 */
public class Row {


	ArrayList<Position> posities;
	int color;
	
	/**
	 * @ensure this.getPositions() != null
	 */
	public Row()
	{
		this.posities = new ArrayList<Position>();
	}
	
	/**
	 * @require getColor() > 0 && getColor() <= 4
	 * @return the color of the positions in this row
	 */
	public int getColor()
	{
		return this.color;
	}
	/**
	 * @ensure color > 0 && color <= 4
	 * @param color the color of the row
	 */
	public void setColor(int color)
	{
		this.color = color;
	}
	
	/**
	 * Add a position to the Row
	 * @ensure this.getPositions().contains(pos)
	 * @param pos the position in the row
	 */
	public void addPosition(Position pos)
	{
		this.posities.add(pos);
	}
	/**
	 * @return the positions in this row
	 */
	public ArrayList<Position> getPositions()
	{
		return posities;
	}
	
	/**
	 * @return the length of the row
	 */
	public int getLength()
	{
		return this.posities.size();
	}
	
	@Override
	public String toString() {
		String string = "|";
		for (Position pos:posities) {
			string = string + pos.getX() + "." + pos.getY() + "|";
		}
		return string;
	}
	
}
