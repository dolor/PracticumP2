package eindopdracht.model;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	int color;
	String name;
	
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
