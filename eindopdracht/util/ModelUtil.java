package eindopdracht.util;

import eindopdracht.model.Position;
import eindopdracht.model.Block;

public class ModelUtil {
	/**
	 * Contains some static methods to convert model-related things. and stuff.
	 */

	private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	
	/**
	 * Converts a letter to an integer index
	 * @param letter
	 * @return
	 */
	public static int letterToInt(String letter) {
		for (int i = 0; i < letters.length; i++) {
			if (letters[i].equals(letter))
				return i;
		}
		System.out.println("[ERROR] Letter " + letter + " could not be found! Returning 0 instead");
		return 0;
	}
	
	/**
	 * Converts an integer index to a letter
	 * @ensure converts 0-8 to a letter A-I for blocks
	 */
	public static String intToLetter(int i) {
		return letters[i];
	}
	
	/**
	 * Converts CW to 1 and CCW to 2.
	 * @param direction
	 * @return
	 */
	public static int directionToInt(String direction) {
		if (direction.equals("CW"))
			return Block.CW;
		else
			return Block.CCW;
	}
	
	/**
	 * @ensure direction == 1 -> return "CW"
	 * @ensure direction != 1 -> return "CCW"
	 */
	public static String intToDirection(int direction) {
		if (direction == Block.CW)
			return "CW";
		else
			return "CCW";
	}
	
	/**
	 * Get the block for the designated position
	 * @param column
	 * @return
	 */
	public static int getBlockForPosition(int row, int column) {
		//TODO move Position to model
		Position pos = new Position(row, column);
		return pos.getBlock();
	}
	
	public static int getTileForPosition(int row, int column) {
		Position pos = new Position(row, column);
		return pos.getTile();
	}
	
	/**
	 * Test to see if the modelutil performs as it should
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < letters.length; i++) {
			String letter = ModelUtil.intToLetter(i);
			int number = ModelUtil.letterToInt(letter);
			System.out.println(i + " > " + letter + " > " + number);
		}
		for (int i = 0; i < 4; i ++) {
			String direction = ModelUtil.intToDirection(i);
			int number = ModelUtil.directionToInt(direction);
			System.out.println(i + " > " + direction + " > " + number);
		}
	}
}
