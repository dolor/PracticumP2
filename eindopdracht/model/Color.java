package eindopdracht.model;

public class Color {
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static final int YELLOW = 4;
	
	public static java.awt.Color getAwtColor(int color) {
		switch (color) {
		case RED:
			return java.awt.Color.red;
		case BLUE:
			return java.awt.Color.blue;
		case GREEN:
			return java.awt.Color.green;
		case YELLOW:
			return java.awt.Color.yellow;
		default:
			return java.awt.Color.white;
		}
	}
}
