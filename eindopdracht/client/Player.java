package eindopdracht.client;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	int color;
	
	
	/**
	 * @ensure 0 <= color <= 4
	 * @param color
	 */
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public int getColor()
	{
		return this.color;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
