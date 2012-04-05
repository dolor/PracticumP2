package eindopdracht.model;

/**
 * Contains the constants of the colors in the game.
 * @author mickvdv
 * 
 */
public class Color {

	public static final int EMPTY = 0; // TODO: Vervang alle 0 door empty
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static final int YELLOW = 4;

	/**
	 * Turns a color (int) into a java.awt color
	 * @require color > 0 && color <= 4
	 * @param color the color that has to be converted into java.awt.Color
	 * @return the java.awt.Color version of our color
	 */
	public static java.awt.Color getAwtColor(int color) {
		switch (color) {
		case RED:
			return java.awt.Color.red;
		case BLUE:
			return java.awt.Color.blue;
		case GREEN:
			return java.awt.Color.green;
		case YELLOW:
			return java.awt.Color.black;
		default:
			return java.awt.Color.white;
		}
	}
}
