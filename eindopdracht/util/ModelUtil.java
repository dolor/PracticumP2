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
	 * @return integer
	 * @require letter A-I
	 */
	public static int letterToInt(String letter) {
		for (int i = 0; i < letters.length; i++) {
			if (letters[i].equals(letter))
				return i;
		}
		PTLog.log("ModelUtil", "[ERROR] Letter " + letter + " could not be found! Returning 0 instead");
		return 0;
	}
	
	/**
	 * Converts an integer index to a letter
	 * @require 0 <= i <= 8
	 * @ensure converts 0-8 to a letter A-I for blocks
	 */
	public static String intToLetter(int i) {
		return letters[i];
	}
	
	/**
	 * @ensure CW -> 1, CCW -> 2.
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
	 * 
	 * @require 0 <= row&column <= 8
	 * @return int block
	 */
	public static int getBlockForPosition(int row, int column) {
		Position pos = new Position(row, column);
		return pos.getBlock();
	}
	
	/**
	 * Get the tile for the designated position
	 *  
	 * @require 0 <= row&column <= 8
	 * @return int tile
	 */
	public static int getTileForPosition(int row, int column) {
		Position pos = new Position(row, column);
		return pos.getTile();
	}
	
	/**
	 * Unit test to see if the modelutil performs as it should
	 * 
	 */
	public static void main(String[] args) {
		for (int i = 0; i < letters.length; i++) {
			String letter = ModelUtil.intToLetter(i);
			int number = ModelUtil.letterToInt(letter);
			PTLog.log("ModelUtil", i + " > " + letter + " > " + number);
		}
		for (int i = 0; i < 4; i ++) {
			String direction = ModelUtil.intToDirection(i);
			int number = ModelUtil.directionToInt(direction);
			PTLog.log("ModelUtil", i + " > " + direction + " > " + number);
		}
	}
}
