package eindopdracht.model;

public class Tile {
	
	int color;
	public Tile()
	{
		this.color = 0;
	}

	public boolean SetColor(int color)
	{
		return SetColor(color, false);
	}
	
	/** @ensure: 	if force == true, => return == true;
	 * 				if GetColor() == null => return == true;
	 * 				if return == true, this.GetColor() == color;
	 *
	 * @param color: color of the tile
	 * @param force: force the tile to place this color. Even when it has to override his color
	 * @return boolean if the color is placed
	 */
	public boolean SetColor(int color, boolean force)
	{
		if (force) // de color wordt geforceerd.
		{
			this.color = color;
			return true;
		}
		else
		{
			if (this.GetColor() == 0)
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
	
	public int GetColor()
	{
		return this.color;
	}
}
