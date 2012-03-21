package eindopdracht.model;

public class Tile {
	
	int color;
	public Tile()
	{
		this.color = 0;
	}

	public boolean SetColor(int color)
	{
		return setColor(color, false);
	}
	
	/** @ensure: 	if 1 <= color <= 4, return == false;
	 * 				if force == true => return == true;
	 * 				if GetColor() == null => return == true;
	 * 				if return == true, this.GetColor() == color;
	 *
	 * @param color: color of the tile
	 * @param force: force the tile to place this color. Even when it has to override his color
	 * @return boolean if the color is placed
	 */
	public boolean setColor(int color, boolean force)
	{
		if (!(1 <= color && color <= 4)) // als color niet tussen 1 en 4 is
		{
			return false;
		}
		
		if (force) // de color wordt geforceerd.
		{
			this.color = color;
			return true;
		}
		else
		{
			if (this.getColor() == 0)
			{
				this.color = color;
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public int getColor()
	{
		return this.color;
	}
}
