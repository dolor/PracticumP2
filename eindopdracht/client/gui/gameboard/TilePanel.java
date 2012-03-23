package eindopdracht.client.gui.gameboard;

import javax.swing.*;

import eindopdracht.model.Color;

public class TilePanel extends JPanel{
	private int color;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		this.setBackground(Color.getAwtColor(color));
	}

}
