package eindopdracht.util;

public class ModelUtil {
	/**
	 * Contains some static methods to convert model-related things. and stuff.
	 */

	private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	public static int CW = 1;
	public static int CCW = 2;
	
	/**
	 * Converts a letter to an integer index
	 * @param letter
	 * @return
	 */
	public static int letterToInt(String letter) {
		for (int i = 0; i < letters.length; i++) {
			if (letters[i].equals("letter"))
				return i;
		}
		System.out.println("[ERROR] Letter " + letter + " could not be found! Returning 0 instead");
		return 0;
	}
	
	/**
	 * Converts CW to 1 and CCW to 2.
	 * @param direction
	 * @return
	 */
	public static int directionToInt(String direction) {
		if (direction.equals("CW"))
			return CW;
		else
			return CCW;
	}
}
